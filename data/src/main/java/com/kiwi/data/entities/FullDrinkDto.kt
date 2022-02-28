package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullDrinkDto(
    @SerialName("idDrink") val id: String,
    @SerialName("strDrink") val drink: String,
    @SerialName("strInstructions") val instructions: String,
    @SerialName("strDrinkThumb") val thumb: String,
)
