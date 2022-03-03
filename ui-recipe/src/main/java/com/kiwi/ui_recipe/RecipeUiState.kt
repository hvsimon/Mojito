package com.kiwi.ui_recipe

import com.kiwi.data.entities.CocktailPo

data class RecipeUiState(
    val cocktail: CocktailPo? = null,
    val isFollowed: Boolean = false,
)
