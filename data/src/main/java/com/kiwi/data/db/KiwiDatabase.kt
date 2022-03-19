package com.kiwi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiwi.data.entities.CategoryEntity
import com.kiwi.data.entities.FollowedRecipe
import com.kiwi.data.entities.FullDrinkEntity

@Database(
    entities = [
        FollowedRecipe::class,
        CategoryEntity::class,
        FullDrinkEntity::class,
    ],
    views = [],
    version = 1,
    autoMigrations = [],
)
@TypeConverters(KiwiTypeConverters::class)
abstract class KiwiDatabase : RoomDatabase() {

    abstract fun cocktailDao(): CocktailDao

    abstract fun followedCocktailDao(): FollowedRecipesDao
}
