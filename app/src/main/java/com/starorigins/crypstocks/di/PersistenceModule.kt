package com.starorigins.crypstocks.di

import android.content.Context
import com.starorigins.crypstocks.data.database.AppDatabase
import com.starorigins.crypstocks.data.database.StocksDao
import com.starorigins.crypstocks.data.datastore.SettingsDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideStocksDao(appDatabase: AppDatabase): StocksDao = appDatabase.stocksDao()

    @Provides
    @Singleton
    fun provideSettingsDataStore(@ApplicationContext context: Context): SettingsDataStore = SettingsDataStore(context)
}
