package com.kiwi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.FollowedRecipe

@Database(
    entities = [
        CocktailPo::class,
        FollowedRecipe::class,
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
