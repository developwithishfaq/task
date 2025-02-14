package com.test.presentation.xml.fragments.favts

import com.test.domain.model.ImageModelUi

sealed class FavtScreenEvents {
    data object ClearAll : FavtScreenEvents()
    data class RemoveFromFav(val model: ImageModelUi) : FavtScreenEvents()
}