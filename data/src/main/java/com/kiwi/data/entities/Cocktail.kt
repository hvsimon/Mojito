package com.kiwi.data.entities

data class Cocktail(
    val id: Long = 0,
    val name: String,
    val gallery: List<String>,
    val intro: String,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val tipsAndTricks: Set<String>,
)
