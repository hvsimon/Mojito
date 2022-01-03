package com.kiwi.data.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.kiwi.data.db.KiwiDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): KiwiDatabase {
//        val builder = Room.inMemoryDatabaseBuilder(context, KiwiDatabase::class.java)
        val builder = Room.databaseBuilder(context, KiwiDatabase::class.java, "kiwi.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Provides
    fun provideCocktailDao(db: KiwiDatabase) = db.cocktailDao()
}
