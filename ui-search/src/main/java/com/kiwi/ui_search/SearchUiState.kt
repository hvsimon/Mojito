package com.kiwi.ui_search

import com.kiwi.data.entities.CocktailPo

data class SearchUiState(
    val query: String = "",
    val categories: List<String> = emptyList(),
    val ingredients: List<String> = emptyList(),
    val randomCocktails: List<CocktailPo> = emptyList(),
    val searchResult: List<CocktailPo> = emptyList(),
    val isSearching: Boolean = false,
    val errorMessage: String? = null,
)
