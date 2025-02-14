package com.test.fortask.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.test.data.repository.FavouriteRepositoryImpl
import com.test.data.repository.FetchPicsRepositoryImpl
import com.test.data.utils.MyPrefs
import com.test.domain.repository.FavouriteRepository
import com.test.domain.repository.FetchPicsRepository
import com.test.persistence.dao.FavouriteDao
import com.test.persistence.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getGlide(
        @ApplicationContext context: Context
    ): RequestManager = Glide.with(context)

    @Provides
    @Singleton
    fun getFetchPics(
        prefs: MyPrefs
    ): FetchPicsRepository = FetchPicsRepositoryImpl(prefs)

    @Provides
    @Singleton
    fun getFavouriteRepository(
        favtDao: FavouriteDao
    ): FavouriteRepository = FavouriteRepositoryImpl(favtDao)

    @Provides
    @Singleton
    fun getDB(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Provides
    @Singleton
    fun getDao(
        db: AppDatabase
    ) = db.getDao()

}