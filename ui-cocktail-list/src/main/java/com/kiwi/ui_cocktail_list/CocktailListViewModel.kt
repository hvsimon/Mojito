package com.kiwi.ui_cocktail_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.repositories.KiwiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CocktailListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    kiwiRepository: KiwiRepository,
) : ViewModel() {

    val list = MutableList(8) {
        Cocktail(
            name = "Cocktail Name",
            gallery = listOf("https://images.unsplash.com/photo-1609345265499-2133bbeb6ce5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1994&q=80"),
            intro = "",
            ingredients = emptyList(),
            steps = emptyList(),
            tips = emptySet(),
        )
    }
}
