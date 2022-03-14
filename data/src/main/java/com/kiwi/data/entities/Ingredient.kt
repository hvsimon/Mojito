package com.kiwi.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val name: String,
    val amount: String,
)
