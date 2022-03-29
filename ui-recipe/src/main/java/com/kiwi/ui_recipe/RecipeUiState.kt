package com.kiwi.ui_recipe

import com.kiwi.data.entities.FullDrinkEntity

data class RecipeUiState(
    val isLoading: Boolean = false,
    val cocktail: FullDrinkEntity? = null,
    val isInstructionsTranslating: Boolean = false,
    val translatedInstructions: String? = null,
    val translatedInstructionsErrorMessage: String? = null,
    val isFollowed: Boolean = false,
    val errorMessage: String? = null,
)
