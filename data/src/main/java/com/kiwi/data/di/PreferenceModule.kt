package com.kiwi.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@InstallIn(SingletonComponent::class)
@Module
class PreferenceModule {

    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext appContext: Context): DataStore<Preferences> {
        return appContext.dataStore
    }
}
