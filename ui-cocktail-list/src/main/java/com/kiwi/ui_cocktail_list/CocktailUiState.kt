package com.kiwi.ui_cocktail_list

data class CocktailUiState(
    val title: String = "",
    val cocktailItems: List<CocktailItemUiState> = listOf(),
)

data class CocktailItemUiState(
    val id: String,
    val name: String,
    val imageUrl: String,
)
