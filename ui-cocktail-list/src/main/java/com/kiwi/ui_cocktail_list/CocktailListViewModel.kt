package com.kiwi.ui_cocktail_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    cocktailRepository: CocktailRepository,
) : ViewModel() {

    private val groupName: String = savedStateHandle["ingredient"]!!

    private val _uiState = MutableStateFlow(CocktailUiState(title = groupName))
    val uiState: StateFlow<CocktailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val list = cocktailRepository.getBaseLiquors()
                .filter { it.baseLiquor == groupName }
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

            _uiState.update {
                it.copy(cocktailItems = list)
            }
        }
    }
}
