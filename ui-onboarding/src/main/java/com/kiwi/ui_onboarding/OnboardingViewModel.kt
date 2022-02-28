package com.kiwi.ui_onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.repositories.KiwiRepository
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
    private val kiwiRepository: KiwiRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    init {
        randomCocktail()

        viewModelScope.launch {
            val list = kiwiRepository.getBaseWines()
            _uiState.update {
                it.copy(baseWines = list)
            }
        }
    }

    fun randomCocktail() {
        viewModelScope.launch {
            val drink = kiwiRepository.randomCocktail().drinks.first()
            _uiState.update {
                it.copy(
                    coverCocktail = Cocktail(
                        cocktailId = drink.id,
                        name = drink.drink,
                        gallery = listOf(drink.thumb),
                        intro = drink.instructions,
                        ingredients = listOf(),
                        steps = listOf(),
                        tips = setOf(),
                    )
                )
            }
        }
    }
}
