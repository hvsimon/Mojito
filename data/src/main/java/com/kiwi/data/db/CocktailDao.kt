package com.kiwi.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.Favorite
import com.kiwi.data.entities.FavoriteAndCocktail

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCocktails(vararg cocktail: CocktailPo)

    @Query("SELECT * FROM CocktailPo WHERE cocktail_id = :cocktailId")
    suspend fun getCocktailBy(cocktailId: String): CocktailPo?

    @Transaction
    @Query("SELECT * FROM favorite")
    fun getFavoritePagingData(): PagingSource<Int, FavoriteAndCocktail>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(vararg favorite: Favorite)
}
