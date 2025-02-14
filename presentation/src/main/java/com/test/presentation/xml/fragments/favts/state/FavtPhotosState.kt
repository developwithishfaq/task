package com.test.presentation.xml.fragments.favts.state

import com.test.domain.model.ImageModelUi

data class FavtPhotosState(
    val photos: List<ImageModelUi> = emptyList()
)