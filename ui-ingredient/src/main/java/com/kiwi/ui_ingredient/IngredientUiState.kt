package com.kiwi.ui_ingredient

data class IngredientUiState(
    val isLoading: Boolean = false,
    val name: String = "",
    val desc: String = "",
    val errorMessage: String? = null,
)
