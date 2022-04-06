package com.kiwi.ui_ingredient

data class IngredientUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val desc: String = "",
    val alsoUseCocktails: List<CocktailUiState> = listOf(),
    val errorMessage: String? = null,
)

data class CocktailUiState(
    val id: String,
    val name: String,
    val imageUrl: String,
)
