package com.kiwi.ui_recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.repositories.CocktailRepository
import com.kiwi.data.repositories.FollowedRecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository,
    private val followedRecipesRepository: FollowedRecipesRepository,
) : ViewModel() {

    private val cocktailId: String = savedStateHandle.get("cocktailId")!!

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val cocktail = cocktailRepository.lookupFullCocktailDetailsById(cocktailId)
            _uiState.update {
                it.copy(cocktail = cocktail)
            }
        }
        viewModelScope.launch {
            followedRecipesRepository.recipeFollowedObservable(cocktailId)
                .collectLatest { isFollowed ->
                    _uiState.update {
                        it.copy(isFollowed = isFollowed)
                    }
                }
        }
    }

    fun toggleFollow() {
        viewModelScope.launch {
            followedRecipesRepository.changeRecipeFollowedStatus(cocktailId)
        }
    }
}
