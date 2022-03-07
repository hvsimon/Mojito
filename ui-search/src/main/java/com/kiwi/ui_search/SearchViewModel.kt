package com.kiwi.ui_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    cocktailRepository: CocktailRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(categories = cocktailRepository.listCategories())
            }
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(ingredients = cocktailRepository.listIngredients())
            }
        }
    }

    fun search(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }
}