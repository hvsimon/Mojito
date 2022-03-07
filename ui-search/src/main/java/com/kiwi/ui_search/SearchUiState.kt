package com.kiwi.ui_search

data class SearchUiState(
    val query: String = "",
    val categories: List<String> = emptyList(),
    val ingredients: List<String> = emptyList(),
)
