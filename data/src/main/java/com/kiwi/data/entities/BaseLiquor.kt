package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseLiquor(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("base_liquor") val baseLiquor: String,
)
