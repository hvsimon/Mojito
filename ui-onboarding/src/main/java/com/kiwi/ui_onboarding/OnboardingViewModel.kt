package com.kiwi.ui_onboarding

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.entities.BaseLiquorGroup
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

    // TODO: find a better way to manage this data
    private val baseLiquorImageMap = mapOf(
        "Rum" to "https://images.unsplash.com/photo-1614313511387-1436a4480ebb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1760&q=80",
        "Gin" to "https://images.unsplash.com/photo-1608885898957-a559228e8749?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80",
        "Vodka" to "https://images.unsplash.com/photo-1550985543-f47f38aeee65?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1335&q=80",
        "Tequila" to "https://images.unsplash.com/photo-1516535794938-6063878f08cc?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=988&q=80",
        "Whiskey" to "https://images.unsplash.com/photo-1527281400683-1aae777175f8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80",
        "Brandy" to "https://images.unsplash.com/photo-1619451050621-83cb7aada2d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=986&q=80",
    )

    init {
        randomCocktail()

        viewModelScope.launch {
            val list = cocktailRepository.getBaseLiquors()
                .groupBy { it.baseLiquor }
                .map { (baseLiquor, list) ->
                    BaseLiquorGroup(
                        groupName = baseLiquor,
                        groupImageUrl = baseLiquorImageMap.getOrElse(baseLiquor) { "" },
                        baseLiquorList = list,
                    )
                }
            _uiState.update {
                it.copy(baseWineGroups = list)
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
