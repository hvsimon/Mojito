package com.kiwi.data.entities

import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val ingredientId: String = UUID.randomUUID().toString(),
    val name: String,
    val amount: String,
)
