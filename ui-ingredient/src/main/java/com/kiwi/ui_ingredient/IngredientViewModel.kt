package com.kiwi.ui_ingredient

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.kiwi.data.entities.FullIngredientEntity
import com.kiwi.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class IngredientViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    ingredientStore: Store<String, FullIngredientEntity>,
) : ViewModel() {

    private val ingredientName = savedStateHandle.get<String>("ingredientName")!!

    private val _uiState = MutableStateFlow(IngredientUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            ingredientStore.stream(StoreRequest.cached(ingredientName, false))
                .collect { response ->
                    when (response) {
                        is StoreResponse.Loading -> {
                            // TODO: show loading status
                        }
                        is StoreResponse.Data -> _uiState.update {
                            val data = response.value
                            it.copy(
                                isLoading = false,
                                name = data.name,
                                desc = data.description ?: "",
                            )
                        }
                        is StoreResponse.Error -> {
                            // TODO: show error status
                        }
                        else -> Unit
                    }
                }
        }
    }
}
