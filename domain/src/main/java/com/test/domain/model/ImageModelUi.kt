package com.test.domain.model

data class ImageModelUi(
    val id: String,
    val author: String,
    val url: String,
    val download_url: String,
    val isFav: Boolean = false
)