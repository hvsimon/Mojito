package com.kiwi.ui_recipe

import com.kiwi.data.entities.FullDrinkEntity

data class RecipeUiState(
    val isLoading: Boolean = false,
    val cocktail: FullDrinkEntity? = null,
    val isInstructionsTranslating: Boolean = false,
    val translatedInstructions: String? = null,
    val translatedInstructionsErrorMessage: String? = null,
    val commonIngredientCocktailsUiState: List<CommonIngredientCocktailsUiState> = listOf(),
    val isFollowed: Boolean = false,
    val errorMessage: String? = null,
)

data class CommonIngredientCocktailsUiState(
    val ingredient: String,
    val cocktails: List<CocktailItemUiState>,
)

data class CocktailItemUiState(
    val id: String,
    val name: String,
    val imageUrl: String,
)
