package com.it.access.di

import com.it.access.data.repository.ItemRepository
import com.it.access.domain.SearchUseCase
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
    fun provideItemRepository() = ItemRepository()

    @Singleton
    @Provides
    fun provideSearchUseCase() = SearchUseCase()
}