package com.test.network.ktor

import android.util.Log
import com.test.framework.core.NetworkResponse
import com.test.network.interfaces.GetPics
import com.test.network.model.ImageModelNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class GetPicsWithKtorImpl : GetPics {
    private val timeOut = 60_000L

    private val TAG = "GetPicsWithKtorImpl"

    private val jsonHelper = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(jsonHelper)
        }
        install(HttpTimeout) {
            connectTimeoutMillis = timeOut
            socketTimeoutMillis = timeOut
            requestTimeoutMillis = timeOut
        }
    }

    override suspend fun getPics(): NetworkResponse<List<ImageModelNetwork>> {
        return try {
            Log.d(TAG, "getPics: ktor called")
            val response =
                client.get("https://picsum.photos/v2/list").body<List<ImageModelNetwork>>()
            Log.d(TAG, "getPics: ktor Response ${response.size}")
            Log.d(TAG, "getPics: ktor Response $response")
            NetworkResponse.Success(response)
        } catch (e: Exception) {
            NetworkResponse.Failure(e.message ?: "")
        }
    }
}