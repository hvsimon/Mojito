package com.kiwi.ui_cocktail_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.repositories.KiwiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
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
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
}