package com.kiwi.ui_ingredient

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.dropbox.android.external.store4.get
import com.kiwi.data.entities.FullIngredientEntity
import com.kiwi.data.entities.SimpleDrinkDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class IngredientViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    ingredientStore: Store<String, FullIngredientEntity>,
    @Named("SearchByIngredient")
    searchByIngredientStore: Store<String, List<SimpleDrinkDto>>,
) : ViewModel() {

    private val ingredientName = savedStateHandle.get<String>("ingredientName")!!

    private val _uiState = MutableStateFlow(IngredientUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            ingredientStore.stream(StoreRequest.cached(ingredientName, false))
                .collect { response ->
                    when (response) {
                        is StoreResponse.Loading -> _uiState.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = null,
                            )
                        }
                        is StoreResponse.Data -> _uiState.update {
                            val data = response.value
                            it.copy(
                                isLoading = false,
                                name = data.name,
                                desc = data.description ?: "",
                                errorMessage = null,
                            )
                        }
                        is StoreResponse.Error.Exception -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = response.error.localizedMessage,
                            )
                        }.also {
                            Timber.e(
                                response.error,
                                "Error while fetching ingredient by name: $ingredientName"
                            )
                        }
                        else -> Unit
                    }
                }
        }

        viewModelScope.launch {
            runCatching {
                searchByIngredientStore.get(ingredientName)
                    .map {
                        CocktailUiState(
                            id = it.id,
                            name = it.name,
                            imageUrl = it.thumb,
                        )
                    }
            }.onSuccess { data ->
                _uiState.update {
                    it.copy(
                        alsoUseCocktails = data
                    )
                }
            }
        }
    }
}
