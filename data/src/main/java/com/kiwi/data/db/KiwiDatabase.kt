package com.kiwi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Ingredient

@Database(
    entities = [
        Cocktail::class,
        Ingredient::class,
    ],
    views = [],
    version = 1,
    autoMigrations = [],
)
@TypeConverters(KiwiTypeConverters::class)
abstract class KiwiDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}
