package com.test.presentation.xml.fragments.favts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.usecases.ClearAllFavtsUseCase
import com.test.domain.usecases.FetchFavtImagesUseCase
import com.test.domain.usecases.SetFavtStatusUseCase
import com.test.presentation.xml.fragments.photos.state.PhotosState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavtsFragmentViewModel @Inject constructor(
    fetchFavts: FetchFavtImagesUseCase,
    private val setFavtStatus: SetFavtStatusUseCase,
    private val clearAll: ClearAllFavtsUseCase
) : ViewModel() {

    private val TAG = "PhotosFragmentViewModel"

    private val _state = MutableStateFlow(PhotosState())
    val state = _state.asStateFlow()

    val images = fetchFavts.invoke().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )

    fun onEvent(events: FavtScreenEvents) {
        when (events) {
            FavtScreenEvents.ClearAll -> {
                viewModelScope.launch {
                    clearAll.invoke()
                }
            }

            is FavtScreenEvents.RemoveFromFav -> {
                viewModelScope.launch {
                    setFavtStatus.invoke(events.model, false)
                }
            }
        }
    }
}