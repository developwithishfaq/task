package com.test.presentation.xml.fragments.photos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.model.ImageModelUi
import com.test.domain.usecases.FetchImagesUseCase
import com.test.domain.usecases.IsFavtUseCase
import com.test.domain.usecases.SetFavtStatusUseCase
import com.test.framework.core.NetworkResponse
import com.test.presentation.xml.fragments.photos.state.PhotosState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosFragmentViewModel @Inject constructor(
    private val fetchImages: FetchImagesUseCase,
    private val setFavtStatus: SetFavtStatusUseCase,
    private val IsFavt: IsFavtUseCase
) : ViewModel() {
    private val TAG = "PhotosFragmentViewModel"

    private val _state = MutableStateFlow(PhotosState())
    val state = _state.asStateFlow()

    init {
        loadImages()
    }

    private fun loadImages() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    photos = NetworkResponse.Loading()
                )
            }
            val data = fetchImages.invoke()
            if (data is NetworkResponse.Success) {
                populateData(data.data ?: emptyList())
            } else {
                Log.d(TAG, "loadImages: $data")
                _state.update {
                    it.copy(
                        photos = data
                    )
                }
            }
        }
    }

    private suspend fun populateData(images: List<ImageModelUi>) {
        val list = images.map {
            it.copy(
                isFav = IsFavt.invoke(it.id)
            )
        }
        _state.update {
            it.copy(
                photos = NetworkResponse.Success(list)
            )
        }
    }

    fun onEvent(events: PhotosScreenEvents) {
        Log.d(TAG, "onEvent: $events")
        when (events) {
            PhotosScreenEvents.Refresh -> {
                val list = state.value.photos.data ?: emptyList()
                viewModelScope.launch {
                    if (list.isEmpty()) {
                        loadImages()
                    } else {
                        populateData(state.value.photos.data ?: emptyList())
                    }
                }
            }

            is PhotosScreenEvents.OnFavt -> {
                viewModelScope.launch {
                    setFavtStatus(model = events.model, favt = events.model.isFav.not())
                    val list = state.value.photos.data?.map {
                        if (it.id == events.model.id) {
                            it.copy(
                                isFav = it.isFav.not()
                            )
                        } else {
                            it
                        }
                    }
                    if (list != null) {
                        _state.update {
                            it.copy(
                                photos = NetworkResponse.Success(list)
                            )
                        }
                    }
                }
            }

            is PhotosScreenEvents.SetImageModel -> {
                _state.update {
                    it.copy(
                        selectedImage = events.model
                    )
                }
            }
        }
    }
}