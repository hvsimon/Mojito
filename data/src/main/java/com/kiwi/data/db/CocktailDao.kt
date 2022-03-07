package com.kiwi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kiwi.data.entities.CocktailPo

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(vararg cocktail: CocktailPo)

    @Query("SELECT * FROM CocktailPo WHERE cocktail_id = :cocktailId")
    suspend fun getCocktailBy(cocktailId: String): CocktailPo?

    @Query(
        """
        SELECT * 
        FROM CocktailPo 
        WHERE cocktail_id 
        IN (SELECT cocktail_id FROM CocktailPo ORDER BY RANDOM() LIMIT :num)
    """
    )
    suspend fun randomCocktail(num: Int): List<CocktailPo>
}
