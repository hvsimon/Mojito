package com.kiwi.ui_recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreRequest
import com.dropbox.android.external.store4.StoreResponse
import com.dropbox.android.external.store4.get
import com.kiwi.data.entities.FullDrinkEntity
import com.kiwi.data.entities.SimpleDrinkDto
import com.kiwi.data.repositories.BrowsingHistoryRepository
import com.kiwi.data.repositories.FollowedRecipesRepository
import com.kiwi.translate.AzureTranslator
import com.kiwi.translate.getTranslationByLang
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltViewModel
class RecipeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val followedRecipesRepository: FollowedRecipesRepository,
    browsingHistoryRepository: BrowsingHistoryRepository,
    @Named("SearchByIngredient")
    private val searchByIngredientStore: Store<String, List<SimpleDrinkDto>>,
    cocktailStore: Store<String, FullDrinkEntity>,
    private val translator: AzureTranslator,
) : ViewModel() {

    private val cocktailId: String = savedStateHandle.get("cocktailId")!!

    private val _uiState = MutableStateFlow(RecipeUiState())
    val uiState: StateFlow<RecipeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            browsingHistoryRepository.record(cocktailId)
        }

        viewModelScope.launch {
            cocktailStore.stream(StoreRequest.cached(key = cocktailId, refresh = false))
                .collect { response ->
                    when (response) {
                        is StoreResponse.Loading -> _uiState.update {
                            it.copy(isLoading = true)
                        }

                        is StoreResponse.Data -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    cocktail = response.value,
                                )
                            }
                            loadCommonIngredientCocktails(response.value.ingredients)
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

    private fun loadCommonIngredientCocktails(ingredients: List<String>) {
        viewModelScope.launch {
            // placeholder
            _uiState.update {
                it.copy(
                    commonIngredientCocktailsUiState = ingredients.map { ingredientName ->
                        CommonIngredientCocktailsUiState(
                            ingredient = ingredientName,
                            cocktails = listOf(),
                        )
                    }
                )
            }

            ingredients
                .asFlow()
                .flatMapConcat { ingredient ->
                    flow {
                        val result = runCatching {
                            searchByIngredientStore.get(ingredient)
                                .map {
                                    CocktailItemUiState(
                                        id = it.id,
                                        name = it.name,
                                        imageUrl = it.thumb,
                                    )
                                }
                        }
                        if (result.isSuccess) {
                            emit(CommonIngredientCocktailsUiState(ingredient, result.getOrThrow()))
                        }
                    }
                }
                .toList()
                .run {
                    _uiState.update {
                        it.copy(commonIngredientCocktailsUiState = this)
                    }
                }
        }
    }

    fun toggleFollow() {
        viewModelScope.launch {
            followedRecipesRepository.changeRecipeFollowedStatus(cocktailId)
        }
    }

    fun translate(text: String, toLang: String = "zh-Hant") {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isInstructionsTranslating = true,
                    translatedInstructions = null,
                    translatedInstructionsErrorMessage = null,
                )
            }
            runCatching {
                withContext(Dispatchers.IO) {
                    translator.translate(text) {
                        to = listOf(toLang)
                    }
                }
            }.onSuccess { data ->
                _uiState.update {
                    it.copy(
                        isInstructionsTranslating = false,
                        translatedInstructions = data.getTranslationByLang(toLang)?.text,
                        translatedInstructionsErrorMessage = null,
                    )
                }
            }.onFailure { t ->
                _uiState.update {
                    it.copy(
                        isInstructionsTranslating = false,
                        translatedInstructions = null,
                        translatedInstructionsErrorMessage =
                        "Translation failed. ${t.localizedMessage}",
                    )
                }.also {
                    Timber.e("Translation failed. ", t)
                }
            }
        }
    }
}
