package com.test.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.test.persistence.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Upsert
    suspend fun saveInFavt(entity: FavouriteEntity)

    @Query("SELECT * FROM FavouriteEntity")
    fun getFavourites(): Flow<List<FavouriteEntity>>

    @Query("SELECT COUNT(*) FROM FAVOURITEENTITY WHERE id = :id")
    suspend fun isIdExists(id: String): Int

    @Query("DELETE FROM FavouriteEntity WHERE id=:id")
    suspend fun delete(id: String)

    @Query("DELETE FROM FavouriteEntity")
    suspend fun deleteAll()
}