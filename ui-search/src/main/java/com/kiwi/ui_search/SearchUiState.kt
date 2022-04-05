package com.kiwi.ui_search

import com.kiwi.data.entities.SimpleDrinkDto

data class SearchUiState(
    val query: String = "",
    val categories: List<String> = emptyList(),
    val ingredients: List<String> = emptyList(),
    val randomCocktails: List<CocktailUiState> = emptyList(),
    val searchByNameResult: SearchResultUiState<CocktailUiState> = SearchResultUiState(),
    val searchByIngredientResult: SearchResultUiState<SimpleDrinkDto> = SearchResultUiState(),
)

data class SearchResultUiState<T>(
    val isSearching: Boolean = false,
    val data: List<T> = emptyList(),
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
