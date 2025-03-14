package com.test.presentation.telenor

import kotlinx.serialization.Serializable

@Serializable
data class TelenorResponse(
    val `data`: Data?=null,
    val error: String?=null,
    val message: String?=null,
    val statusCode: String?=null
)