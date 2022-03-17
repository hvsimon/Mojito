package com.kiwi.ui_recipe

import com.kiwi.data.entities.CocktailPo

data class RecipeUiState(
    val isLoading: Boolean = false,
    val cocktail: CocktailPo? = null,
    val isFollowed: Boolean = false,
    val errorMessage: String? = null,
)
