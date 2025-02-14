package com.test.domain.repository

import com.test.domain.model.ImageModelUi
import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {
    suspend fun saveAsFavourite(imageModelUi: ImageModelUi)
    suspend fun delete(id: String)
    suspend fun deleteAll()
    suspend fun isFavourite(id: String): Boolean
    fun getAllFavourites(): Flow<List<ImageModelUi>>
}