package com.test.persistence.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.persistence.dao.FavouriteDao
import com.test.persistence.entity.FavouriteEntity

@Database(entities = [FavouriteEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): FavouriteDao
}