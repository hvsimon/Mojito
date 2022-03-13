package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IBACocktail(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("iba") val iba: String,
)
