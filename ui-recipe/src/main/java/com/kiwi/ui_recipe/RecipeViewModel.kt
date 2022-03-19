package com.kiwi.ui_recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.kiwi.data.entities.FullDrinkEntity
import com.kiwi.data.repositories.FollowedRecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class RecipeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val followedRecipesRepository: FollowedRecipesRepository,
    cocktailStore: Store<String, FullDrinkEntity>
) : ViewModel() {

    private val cocktailId: String = savedStateHandle.get("cocktailId")!!

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            cocktailStore.stream(StoreRequest.cached(key = cocktailId, refresh = false))
                .collect { response ->
                    when (response) {
                        is StoreResponse.Loading -> _uiState.update {
                            it.copy(isLoading = true)
                        }

                        is StoreResponse.Data -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                cocktail = response.value,
                            )
                        }
                        is StoreResponse.Error.Exception -> _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = response.errorMessageOrNull()
                                    ?: "TODO: unknown error",
                            )
                        }.also {
                            Timber.e(
                                response.error,
                                "Error while fetching cocktail by id: $cocktailId"
                            )
                        }
                        else -> Unit
                    }
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
