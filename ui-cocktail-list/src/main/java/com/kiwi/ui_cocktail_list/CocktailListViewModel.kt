package com.kiwi.ui_cocktail_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.repositories.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    cocktailRepository: CocktailRepository,
) : ViewModel() {

    val groupName: String = savedStateHandle["ingredient"]!!

    val list = flow {
        cocktailRepository.getBaseLiquors()
            .filter { it.baseLiquor == groupName }
            .map { viewModelScope.async { cocktailRepository.searchByIngredient(it.name) } }
            .awaitAll()
            .flatten()
            .run { emit(this) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}
