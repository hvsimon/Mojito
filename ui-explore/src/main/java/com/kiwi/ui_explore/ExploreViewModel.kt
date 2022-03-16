package com.kiwi.ui_explore

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ExploreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExploreUiState())
    val uiState: StateFlow<ExploreUiState> = _uiState.asStateFlow()

    init {
        randomCocktail()

        viewModelScope.launch {
            val list = cocktailRepository.getBaseLiquors()
                .groupBy { it.baseLiquor }
                .keys
                .map { BaseLiquorItemUiState(it) }
            _uiState.update {
                it.copy(baseLiquorItemUiStates = list)
            }
        }

        viewModelScope.launch {
            val list = cocktailRepository.getIBACocktails()
                .groupBy { it.iba }
                .keys
                .map { IBACategoryItemUiState(it) }
            _uiState.update {
                it.copy(ibaCategoryUiStates = list)
            }
        }
    }

    fun randomCocktail() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isRefreshing = true)
            }

            val cocktail = cocktailRepository.randomCocktail(num = 1, loadFromRemote = true).first()
            _uiState.update {
                it.copy(
                    coverCocktail = cocktail,
                    isRefreshing = false,
                )
            }
        }
    }
}
