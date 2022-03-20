package com.kiwi.ui_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.kiwi.data.entities.CategoryEntity
import com.kiwi.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    categoryStore: Store<Unit, List<CategoryEntity>>,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            categoryStore.stream(StoreRequest.cached(Unit, false))
                .collect { response ->
                    when (response) {
                        is StoreResponse.Data -> _uiState.update {
                            it.copy(
                                categories = response.value
                                    .map { categoryEntity -> categoryEntity.name }
                            )
                        }

                        is StoreResponse.Error.Exception -> {
                            Timber.e(
                                response.error,
                                "Error while fetching categories on Search"
                            )
                        }
                        else -> Unit
                    }
                }
        }

        viewModelScope.launch {
            cocktailRepository.randomCocktail(10, false)
                .onSuccess { data ->
                    _uiState.update {
                        it.copy(randomCocktails = data)
                    }
                }
                .onFailure { t ->
                    Timber.e(t, "Error while random cocktail for show on Search")
                    // ignore this error for user
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
                    errorMessage = result.exceptionOrNull()
                        ?.also { t -> Timber.e(t, "Error while searching: $query") }
                        ?.localizedMessage,
                )
            }
        }
    }
}
