package com.kiwi.ui_recipe

import com.kiwi.data.entities.FullDrinkEntity

data class RecipeUiState(
    val isLoading: Boolean = false,
    val cocktail: FullDrinkEntity? = null,
    val isFollowed: Boolean = false,
    val errorMessage: String? = null,
)
