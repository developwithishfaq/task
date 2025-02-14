package com.test.domain.repository

import com.test.domain.model.ImageModelUi
import com.test.framework.core.NetworkResponse

interface FetchPicsRepository {
    suspend fun getPictures(): NetworkResponse<List<ImageModelUi>>
}