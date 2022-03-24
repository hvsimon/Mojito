package com.kiwi.ui_search

data class SearchUiState(
    val query: String = "",
    val categories: List<String> = emptyList(),
    val ingredients: List<String> = emptyList(),
    val randomCocktails: List<CocktailUiState> = emptyList(),
    val searchResult: List<CocktailUiState> = emptyList(),
    val isSearching: Boolean = false,
    val errorMessage: String? = null,
)

data class CocktailUiState(
    val id: String,
    val name: String,
    val thumb: String,
    val ingredients: List<String>? = null,
    val instructions: String? = null,
    val category: String,
)
