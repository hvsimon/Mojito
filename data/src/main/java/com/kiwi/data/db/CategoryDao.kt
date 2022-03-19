package com.kiwi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kiwi.data.entities.CategoryEntity

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(vararg cocktail: CategoryEntity)

    @Query("SELECT * FROM CategoryEntity")
    suspend fun getAllCocktailCategory(): List<CategoryEntity>
}
