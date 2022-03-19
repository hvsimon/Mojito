package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A response from https://www.thecocktaildb.com/api.php
 */
@Serializable
data class TheCocktailDBResponse<T>(
    @SerialName("drinks") val drinks: List<T> = emptyList(),
    @SerialName("ingredients") val ingredients: List<T> = emptyList(),
)
