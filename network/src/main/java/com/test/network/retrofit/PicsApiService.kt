package com.test.network.retrofit

import com.test.network.model.ImageModelNetwork
import retrofit2.http.GET

interface PicsApiService {

    @GET("v2/list")
    suspend fun getImages(): List<ImageModelNetwork>
}
