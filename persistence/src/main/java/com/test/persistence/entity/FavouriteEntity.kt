package com.test.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavouriteEntity(
    @PrimaryKey
    val id: String,
    val author: String,
    val url: String,
    val download_url: String
)
