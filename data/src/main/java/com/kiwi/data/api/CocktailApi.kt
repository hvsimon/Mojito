package com.kiwi.data.api

import com.kiwi.data.entities.CocktailResponse
import com.kiwi.data.entities.FullDrinkDto
import com.kiwi.data.entities.SimpleDrinkDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CocktailApi {

    @Headers("Accept-Encoding: identity")
    @GET("random.php")
    suspend fun randomCocktail(): CocktailResponse<FullDrinkDto>

    @Headers("Accept-Encoding: identity")
    @GET("filter.php")
    suspend fun searchByIngredient(
        @Query("i") ingredientName: String
    ): CocktailResponse<SimpleDrinkDto>


}
