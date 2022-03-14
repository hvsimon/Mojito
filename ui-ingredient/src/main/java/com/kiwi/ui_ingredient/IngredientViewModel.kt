package com.kiwi.ui_ingredient

import androidx.lifecycle.SavedStateHandle
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
class IngredientViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    cocktailRepository: CocktailRepository,
) : ViewModel() {

    private val ingredientName = savedStateHandle.get<String>("ingredientName")!!

    private val _uiState = MutableStateFlow(IngredientUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val data = cocktailRepository.searchIngredientByName(ingredientName)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    name = data.ingredient,
                    desc = data.description ?: "",
                )
            }
        }
    }
}
