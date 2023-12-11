package com.starorigins.crypstocks.di

import com.starorigins.crypstocks.data.repositories.stocks.IEXService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideIEXService(): IEXService = IEXService.create()
}
