package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleIngredientDto(
    @SerialName("strIngredient1") val ingredientName: String,
)
