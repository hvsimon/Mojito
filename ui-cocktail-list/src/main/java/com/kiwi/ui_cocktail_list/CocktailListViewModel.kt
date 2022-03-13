package com.kiwi.ui_cocktail_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.common_ui_compose.CocktailListFilterType
import com.kiwi.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cocktailRepository: CocktailRepository,
) : ViewModel() {

    private val filter: CocktailListFilterType =
        savedStateHandle.get<CocktailListFilterType>("filter")!!
    private val keyword: String = savedStateHandle["keyword"]!!

    private val _uiState = MutableStateFlow(CocktailUiState(title = keyword))
    val uiState: StateFlow<CocktailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val list = when (filter) {
                CocktailListFilterType.BASE_LIQUOR -> getCocktailsByBaseLiquors()
                CocktailListFilterType.IBA_CATEGORY -> getIBACocktails()
            }

            _uiState.update {
                it.copy(cocktailItems = list)
            }
        }
    }

    private suspend fun getCocktailsByBaseLiquors() =
        cocktailRepository.getBaseLiquors()
            .filter { it.baseLiquor == keyword }
            .map { viewModelScope.async { cocktailRepository.searchByIngredient(it.name) } }
            .awaitAll()
            .flatten()
            .map {
                CocktailItemUiState(
                    id = it.cocktailId,
                    name = it.name,
                    imageUrl = it.gallery.firstOrNull() ?: "",
                )
            }

    private suspend fun getIBACocktails() =
        cocktailRepository.getIBACocktails()
            .filter { it.iba == keyword && it.id.isNotEmpty() }
            .map {
                viewModelScope.async { cocktailRepository.lookupFullCocktailDetailsById(it.id) }
            }
            .awaitAll()
            .map {
                CocktailItemUiState(
                    id = it.cocktailId,
                    name = it.name,
                    imageUrl = it.gallery.firstOrNull() ?: "",
                )
            }
}
