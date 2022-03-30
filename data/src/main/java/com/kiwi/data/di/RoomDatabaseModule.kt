package com.kiwi.data.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.kiwi.data.db.BrowsingHistoryDao
import com.kiwi.data.db.CategoryDao
import com.kiwi.data.db.CocktailDao
import com.kiwi.data.db.FollowedRecipesDao
import com.kiwi.data.db.IngredientDao
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
        @ApplicationContext context: Context,
    ): KiwiDatabase {
        val builder = Room.databaseBuilder(context, KiwiDatabase::class.java, "kiwi.db")
            .fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideCocktailDao(db: KiwiDatabase): CocktailDao = db.cocktailDao()

    @Singleton
    @Provides
    fun provideIngredientDao(db: KiwiDatabase): IngredientDao = db.ingredientDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db: KiwiDatabase): CategoryDao = db.categoryDao()

    @Singleton
    @Provides
    fun provideFollowedCocktailDao(db: KiwiDatabase): FollowedRecipesDao = db.followedCocktailDao()

    @Singleton
    @Provides
    fun provideBrowsingHistoryDao(db: KiwiDatabase): BrowsingHistoryDao = db.browsingHistoryDao()
}
