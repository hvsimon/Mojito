package com.kiwi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiwi.data.entities.BrowsingHistoryEntity
import com.kiwi.data.entities.CategoryEntity
import com.kiwi.data.entities.FollowedRecipe
import com.kiwi.data.entities.FullDrinkEntity
import com.kiwi.data.entities.FullIngredientEntity

@Database(
    entities = [
        FollowedRecipe::class,
        CategoryEntity::class,
        FullDrinkEntity::class,
        FullIngredientEntity::class,
        BrowsingHistoryEntity::class,
    ],
    views = [],
    version = 2,
    autoMigrations = [],
)
@TypeConverters(KiwiTypeConverters::class)
abstract class KiwiDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao

    abstract fun ingredientDao(): IngredientDao

    abstract fun categoryDao(): CategoryDao

    abstract fun followedCocktailDao(): FollowedRecipesDao

    abstract fun browsingHistoryDao(): BrowsingHistoryDao
}
