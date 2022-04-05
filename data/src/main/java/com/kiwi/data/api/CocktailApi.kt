package com.kiwi.data.api

import com.kiwi.data.entities.CategoryEntity
import com.kiwi.data.entities.FullDrinkEntity
import com.kiwi.data.entities.FullIngredientEntity
import com.kiwi.data.entities.SimpleDrinkDto
import com.kiwi.data.entities.SimpleIngredientDto
import com.kiwi.data.entities.TheCocktailDBResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CocktailApi {

    @Headers("Accept-Encoding: identity")
    @GET("random.php")
    suspend fun randomCocktail(): TheCocktailDBResponse<FullDrinkEntity>

    @Headers("Accept-Encoding: identity")
    @GET("search.php")
    suspend fun searchCocktailByName(
        @Query("s") cocktailName: String,
    ): TheCocktailDBResponse<FullDrinkEntity>

    @Headers("Accept-Encoding: identity")
    @GET("search.php")
    suspend fun searchCocktailByFirstLetter(
        @Query("f") firstLetter: Char,
    ): TheCocktailDBResponse<FullDrinkEntity>

    @Headers("Accept-Encoding: identity")
    @GET("search.php")
    suspend fun searchIngredientByName(
        @Query("i") ingredientName: String,
    ): TheCocktailDBResponse<FullIngredientEntity>

    @Headers("Accept-Encoding: identity")
    @GET("filter.php")
    suspend fun searchCocktailByIngredient(
        @Query("i") ingredientName: String
    ): TheCocktailDBResponse<SimpleDrinkDto>

    @Headers("Accept-Encoding: identity")
    @GET("filter.php")
    suspend fun filterByCategory(
        @Query("c") category: String
    ): TheCocktailDBResponse<SimpleDrinkDto>

    @Headers("Accept-Encoding: identity")
    @GET("lookup.php")
    suspend fun lookupFullCocktailDetailsById(
        @Query("i") id: String
    ): TheCocktailDBResponse<FullDrinkEntity>

    @Headers("Accept-Encoding: identity")
    @GET("list.php?c=list")
    suspend fun listCategories(): TheCocktailDBResponse<CategoryEntity>

    @Headers("Accept-Encoding: identity")
    @GET("list.php?i=list")
    suspend fun listIngredients(): TheCocktailDBResponse<SimpleIngredientDto>
}
