package com.kiwi.ui_collection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Favorite
import com.kiwi.data.entities.Ingredient
import com.kiwi.data.repositories.KiwiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@HiltViewModel
class CollectionViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    kiwiRepository: KiwiRepository,
) : ViewModel() {

    val pagedList: Flow<PagingData<Favorite>> = flowOf(
        PagingData.from(
            MutableList(cocktailNames.size) { index ->
                Favorite(
                    cocktail = cocktails[index],
                    catalog = categories.random()
                )
            }.sortedBy { it.catalog }
        )
    )
}

private val cocktailNames = listOf(
    "Alexander",
    "Daiquiri",
    "Negroni",
    "Screwdriver",
    "Americano",
    "Derby",
    "Old Fashioned",
)

private val ingredientNames = listOf(
    "Gin",
    "Campari",
    "Red Vermouth",
    "Bourbon Whiskey",
    "Simple Syrup",
    "Angostura Bitters",
    "Cointreau",
    "Lemon Juice",
)

private val ingredients = ingredientNames.map {
    Ingredient(
        name = it,
        amount = ""
    )
}

private val categories =
    listOf("Unforgettable Cocktails", "Contemporary Classic Cocktails", "New Era Cocktails")

private val cocktails = cocktailNames.map {
    Cocktail(
        name = it,
        intro = "",
        gallery = emptyList(),
        ingredients = ingredients.shuffled().take((1..ingredients.size).random()),
        steps = emptyList(),
        tipsAndTricks = emptySet(),
    )
}
