package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleDrinkDto(
    @SerialName("idDrink") val id: String,
    @SerialName("strDrink") val drink: String,
    @SerialName("strDrinkThumb") val thumb: String,
)
