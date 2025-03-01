package com.test.presentation.test

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class KtorHelper {
    private val timeOut = 60_000L
    private val jsonHelper = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    fun getHelper() = jsonHelper

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

    suspend fun hitAndGetResponse(
        url: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
    ): String? {
        return withContext(Dispatchers.IO) {
            try {
                client.post(url) {
                    headers.forEach {
                        header(it.key, it.value)
                    }
                    if (body != null) {
                        setBody(body)
                    }
                }.body()
            } catch (_: Exception) {
                null
            }
        }
    }
}