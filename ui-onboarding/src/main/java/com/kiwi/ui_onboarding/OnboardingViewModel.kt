package com.kiwi.ui_onboarding

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
class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        randomCocktail()

        viewModelScope.launch {
            val list = cocktailRepository.getBaseWineGroups()
            _uiState.update {
                it.copy(baseWineGroups = list)
            }
        }

        viewModelScope.launch {
            val list = cocktailRepository.getCategories()
            _uiState.update {
                it.copy(categories = list)
            }
        }
    }

    fun randomCocktail() {
        viewModelScope.launch {
            val cocktail = cocktailRepository.randomCocktail()
            _uiState.update {
                it.copy(coverCocktail = cocktail)
            }
        }
    }
}
