package com.kiwi.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiwi.data.entities.Favorite
import com.kiwi.data.entities.CocktailPo

@Database(
    entities = [
        CocktailPo::class,
        Favorite::class,
    ],
    views = [],
    version = 1,
    autoMigrations = [],
)
@TypeConverters(KiwiTypeConverters::class)
abstract class KiwiDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}
