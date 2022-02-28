package com.kiwi.ui_recipe

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Ingredient
import com.kiwi.data.repositories.KiwiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class RecipeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    kiwiRepository: KiwiRepository,
) : ViewModel() {

    private val cocktailId: String = savedStateHandle.get("cocktailId")!!

    val cocktail = flow { emit(kiwiRepository.lookupFullCocktailDetailsById(cocktailId)) }
        .map { drink ->
            Cocktail(
                cocktailId = drink.id,
                name = drink.drink,
                gallery = listOf(drink.thumb),
                intro = "TODO",
                ingredients = drink.ingredients.zip(drink.measures) { ingredient, measure ->
                    Ingredient(
                        name = ingredient,
                        amount = measure,
                    )
                },
                steps = listOf(drink.instructions),
                tips = setOf(),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null,
        )
}
