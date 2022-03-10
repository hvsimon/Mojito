package com.kiwi.data.api

import com.kiwi.data.entities.CategoryDto
import com.kiwi.data.entities.CocktailIngredientDto
import com.kiwi.data.entities.CocktailResponse
import com.kiwi.data.entities.FullDrinkDto
import com.kiwi.data.entities.FullIngredientDto
import com.kiwi.data.entities.SimpleDrinkDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CocktailApi {

    @Headers("Accept-Encoding: identity")
    @GET("random.php")
    suspend fun randomCocktail(): CocktailResponse<FullDrinkDto>

    @Headers("Accept-Encoding: identity")
    @GET("search.php")
    suspend fun searchCocktailByName(
        @Query("s") cocktailName: String,
    ): CocktailResponse<FullDrinkDto>

    @Headers("Accept-Encoding: identity")
    @GET("search.php")
    suspend fun searchIngredientByName(
        @Query("i") ingredientName: String,
    ): CocktailResponse<FullIngredientDto>

    @Headers("Accept-Encoding: identity")
    @GET("filter.php")
    suspend fun searchByIngredient(
        @Query("i") ingredientName: String
    ): CocktailResponse<SimpleDrinkDto>

    @Headers("Accept-Encoding: identity")
    @GET("filter.php")
    suspend fun filterByCategory(
        @Query("c") category: String
    ): CocktailResponse<SimpleDrinkDto>

    @Headers("Accept-Encoding: identity")
    @GET("lookup.php")
    suspend fun lookupFullCocktailDetailsById(
        @Query("i") id: String
    ): CocktailResponse<FullDrinkDto>

    @Headers("Accept-Encoding: identity")
    @GET("list.php?c=list")
    suspend fun listCategories(): CocktailResponse<CategoryDto>

    @Headers("Accept-Encoding: identity")
    @GET("list.php?i=list")
    suspend fun listIngredients(): CocktailResponse<CocktailIngredientDto>
}
