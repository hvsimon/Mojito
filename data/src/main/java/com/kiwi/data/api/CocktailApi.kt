package com.kiwi.data.api

import com.kiwi.data.entities.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Headers

interface CocktailApi {

    @Headers("Accept-Encoding: identity")
    @GET("random.php")
    suspend fun randomCocktail(): CocktailResponse
}
