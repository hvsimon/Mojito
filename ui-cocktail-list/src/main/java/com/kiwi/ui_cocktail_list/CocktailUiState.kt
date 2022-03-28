package com.kiwi.ui_cocktail_list

data class CocktailUiState(
    val titleStringRes: Int? = null,
    val cocktailItems: List<CocktailItemUiState> = listOf(),
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
)

data class CocktailItemUiState(
    val id: String,
    val name: String,
    val imageUrl: String,
)
