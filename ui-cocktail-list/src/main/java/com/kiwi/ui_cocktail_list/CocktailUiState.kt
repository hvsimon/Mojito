package com.kiwi.ui_cocktail_list

data class CocktailUiState(
    val titleStringRes: Int = -1,
    val cocktailItems: List<CocktailItemUiState> = listOf(),
    val errorMessage: String? = null,
)

data class CocktailItemUiState(
    val id: String,
    val name: String,
    val imageUrl: String,
)
