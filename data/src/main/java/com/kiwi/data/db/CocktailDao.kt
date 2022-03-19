package com.kiwi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kiwi.data.entities.CategoryEntity
import com.kiwi.data.entities.FullDrinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(vararg cocktail: FullDrinkEntity)

    @Query("SELECT * FROM FullDrinkEntity WHERE idDrink = :cocktailId")
    fun getCocktailBy(cocktailId: String): FullDrinkEntity?

    @Query("SELECT * FROM FullDrinkEntity WHERE idDrink = :cocktailId")
    fun getCocktailByIdFlow(cocktailId: String): Flow<FullDrinkEntity>

    @Query(
        """
        SELECT * 
        FROM FullDrinkEntity
        WHERE idDrink
        IN (SELECT idDrink FROM FullDrinkEntity ORDER BY RANDOM() LIMIT :num)
    """
    )
    suspend fun randomCocktail(num: Int): List<FullDrinkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(vararg cocktail: CategoryEntity)

    @Query("SELECT * FROM CategoryEntity")
    fun getAllCocktailCategoryFlow(): Flow<List<CategoryEntity>>
}
