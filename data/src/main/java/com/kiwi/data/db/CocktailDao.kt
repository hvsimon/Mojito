package com.kiwi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kiwi.data.entities.Cocktail

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(vararg cocktail: Cocktail)

    @Query("SELECT * FROM Cocktail WHERE cocktail_id = :cocktailId")
    fun getCocktailBy(cocktailId: String): Cocktail

    @Query("SELECT * FROM Cocktail ORDER BY RANDOM() LIMIT 1")
    fun getRandomCocktail(): Cocktail
}
