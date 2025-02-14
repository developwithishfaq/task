package com.test.presentation.xml.fragments.photos.state

import com.test.domain.model.ImageModelUi
import com.test.framework.core.NetworkResponse

data class PhotosState(
    val photos: NetworkResponse<List<ImageModelUi>> = NetworkResponse.Idle(),
    val selectedImage: ImageModelUi? = null
)
