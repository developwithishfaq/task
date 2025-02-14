package com.test.presentation.xml.fragments.photos

import com.test.domain.model.ImageModelUi

sealed class PhotosScreenEvents {
    data object Refresh : PhotosScreenEvents()
    data class OnFavt(val model: ImageModelUi) : PhotosScreenEvents()
    data class SetImageModel(val model: ImageModelUi?) : PhotosScreenEvents()
}