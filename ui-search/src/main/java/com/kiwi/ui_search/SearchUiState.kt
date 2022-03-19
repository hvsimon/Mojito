package com.kiwi.ui_search

import com.kiwi.data.entities.FullDrinkEntity

data class SearchUiState(
    val query: String = "",
    val categories: List<String> = emptyList(),
    val ingredients: List<String> = emptyList(),
    val randomCocktails: List<FullDrinkEntity> = emptyList(),
    val searchResult: List<FullDrinkEntity> = emptyList(),
    val isSearching: Boolean = false,
    val errorMessage: String? = null,
)
