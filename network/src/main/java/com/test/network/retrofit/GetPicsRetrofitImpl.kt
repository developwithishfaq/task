package com.test.network.retrofit

import android.util.Log
import com.test.framework.core.NetworkResponse
import com.test.network.interfaces.GetPics
import com.test.network.model.ImageModelNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPicsRetrofitImpl : GetPics {
    private val TAG = "GetPicsRetrofitImpl"
    override suspend fun getPics(): NetworkResponse<List<ImageModelNetwork>> {
        return withContext(Dispatchers.IO) {
            Log.d(TAG, "getPics: Called")
            try {
                val response = RetrofitClient.apiService.getImages()
                Log.d(TAG, "getPics: Response ${response.size}")
                Log.d(TAG, "getPics: Response $response")
                NetworkResponse.Success(response)
            } catch (e: Exception) {
                NetworkResponse.Failure(e.message ?: "")
            }
        }
    }
}