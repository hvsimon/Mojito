package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CocktailResponse<T>(
    @SerialName("drinks") val drinks: List<T>,
)
