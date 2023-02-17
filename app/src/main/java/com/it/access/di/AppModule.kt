package com.it.access.di

import android.app.Application
import androidx.room.Room
import com.it.access.data.AppDatabase
import com.it.access.data.dao.ItemDao
import com.it.access.data.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideDb(appContext: Application) = Room.databaseBuilder(
            appContext,
            AppDatabase::class.java, "item-db"
        ).build()

    @Singleton
    @Provides
    fun provideItemDao(db: AppDatabase) = db.itemDao()

    @Singleton
    @Provides
    fun provideItemRepository(dao: ItemDao) = ItemRepository(dao)
}