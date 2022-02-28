package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailResponse(
    @SerialName("drinks") val drinks: List<DrinkDto>,
)

@Serializable
data class DrinkDto(
    @SerialName("idDrink") val id: String,
    @SerialName("strDrink") val drink: String,
    @SerialName("strInstructions") val instructions: String,
    @SerialName("strDrinkThumb") val thumb: String,
)
