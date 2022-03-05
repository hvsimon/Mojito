package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullIngredientDto(
    @SerialName("idIngredient") val id: String,
    @SerialName("strIngredient") val ingredient: String,
    @SerialName("strDescription") val description: String? = null,
    @SerialName("strType") val type: String? = null,
)
