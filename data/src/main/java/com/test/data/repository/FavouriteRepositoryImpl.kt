package com.test.data.repository

import com.test.data.dto.toFavEntity
import com.test.data.dto.toUiModel
import com.test.domain.model.ImageModelUi
import com.test.domain.repository.FavouriteRepository
import com.test.persistence.dao.FavouriteDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouriteRepositoryImpl(
    private val favouriteDao: FavouriteDao
) : FavouriteRepository {
    override suspend fun saveAsFavourite(imageModelUi: ImageModelUi) {
        favouriteDao.saveInFavt(imageModelUi.toFavEntity())
    }

    override suspend fun delete(id: String) {
        favouriteDao.delete(id)
    }

    override suspend fun deleteAll() {
        favouriteDao.deleteAll()
    }

    override suspend fun isFavourite(id: String): Boolean {
        return favouriteDao.isIdExists(id) > 0
    }

    override fun getAllFavourites(): Flow<List<ImageModelUi>> {
        return favouriteDao.getFavourites().map {
            it.map { it.toUiModel() }
        }
    }
}