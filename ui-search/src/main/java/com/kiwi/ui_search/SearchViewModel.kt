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
    private val cocktailRepository: CocktailRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    categories = cocktailRepository.listCategories()
                        .map { cocktailCategoryPo -> cocktailCategoryPo.name }
                )
            }
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(ingredients = cocktailRepository.listIngredients())
            }
        }

        viewModelScope.launch {
            cocktailRepository.randomCocktail(10, false)
                .onSuccess { data ->
                    _uiState.update {
                        it.copy(randomCocktails = data)
                    }
                }
                .onFailure {
                    // TODO: pass error message
                }

        }
    }

    fun search(query: String) {
        if (query.isBlank()) {
            _uiState.update {
                it.copy(
                    query = "",
                    searchResult = emptyList(),
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(isSearching = true)
            }
            val result = cocktailRepository.searchCocktailByName(query)
            _uiState.update {
                it.copy(
                    isSearching = false,
                    query = query,
                    searchResult = result.getOrDefault(emptyList()),
                    errorMessage = result.exceptionOrNull()?.localizedMessage,
                )
            }
        }
    }
}
