package com.kiwi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kiwi.data.entities.FullIngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Query("SELECT * FROM FullIngredientEntity WHERE strIngredient = :ingredientName")
    fun getIngredientByNameFlow(ingredientName: String): Flow<FullIngredientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(vararg ingredient: FullIngredientEntity)
}
