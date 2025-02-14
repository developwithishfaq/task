package com.test.network.interfaces

import com.test.framework.core.NetworkResponse
import com.test.network.model.ImageModelNetwork

interface GetPics {

    suspend fun getPics(): NetworkResponse<List<ImageModelNetwork>>
}