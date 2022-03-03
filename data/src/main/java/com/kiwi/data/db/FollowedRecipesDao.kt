package com.kiwi.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kiwi.data.entities.FollowedRecipe
import com.kiwi.data.entities.FollowedRecipeAndCocktail
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowedRecipesDao {

    @Query("SELECT COUNT(*) FROM followedRecipe WHERE cocktail_id = :cocktailId")
    fun followedCountByCocktailIdObservable(cocktailId: String): Flow<Int>

    @Transaction
    @Query("SELECT * FROM followedRecipe ORDER BY datetime(followed_at) DESC")
    fun getFollowedPagingData(): PagingSource<Int, FollowedRecipeAndCocktail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateFollowed(vararg followedRecipe: FollowedRecipe)

    @Query("DELETE FROM followedRecipe WHERE cocktail_id IN (:cocktailId)")
    suspend fun deleteFollowed(vararg cocktailId: String)
}
