package com.kiwi.ui_cocktail_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.repositories.KiwiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    kiwiRepository: KiwiRepository,
) : ViewModel() {

    val title: String = savedStateHandle["ingredient"]!!

    val list = flow {
        val data = kiwiRepository.searchByIngredient(title)
        emit(data)
    }.map {
        it.map { drink ->
            Cocktail(
                cocktailId = drink.id,
                name = drink.drink,
                gallery = listOf(drink.thumb),
                intro = "",
                ingredients = listOf(),
                steps = listOf(),
                tips = setOf(),
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}