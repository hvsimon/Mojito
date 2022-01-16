package com.kiwi.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.CocktailIngredientCrossRef
import com.kiwi.data.entities.Ingredient
import com.kiwi.data.entities.RecipeEntity

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(vararg cocktail: Cocktail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(vararg ingredient: Ingredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktailIngredientCrossRef(vararg cocktailIngredientCrossRef: CocktailIngredientCrossRef)

    @Transaction
    suspend fun insertRecipe(cocktail: Cocktail, ingredients: List<Ingredient>) {
//        insertCocktails(cocktail)
//        insertIngredients(*ingredients.toTypedArray())

        val ref = ingredients.map {
            CocktailIngredientCrossRef(
                cocktailId = cocktail.cocktailId,
                ingredientId = it.ingredientId,
            )
        }
        insertCocktailIngredientCrossRef(*ref.toTypedArray())
    }

    @Query("SELECT * FROM Cocktail WHERE cocktailId = :cocktailId")
    fun getRecipeBy(cocktailId: Long): RecipeEntity

    @Query("SELECT * FROM Cocktail WHERE cocktailId = :cocktailId")
    fun getCocktailBy(cocktailId: String): Cocktail

    @Query("SELECT * FROM Cocktail ORDER BY RANDOM() LIMIT 1")
    fun getRandomCocktail(): Cocktail
}
