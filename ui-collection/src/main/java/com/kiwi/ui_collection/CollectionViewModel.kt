package com.kiwi.ui_collection

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Favorite
import com.kiwi.data.entities.Ingredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CollectionViewModel : ViewModel() {

    val pagedList: Flow<PagingData<Favorite>> = flow {
        PagingData.from(
            MutableList(cocktailNames.size) { index ->
                Favorite(
                    cocktail = cocktails[index],
                    catalog = categories.random()
                )
            }
        )
    }
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

private val categories = listOf("Unforgettable Cocktails", "Contemporary Classic Cocktails", "New Era Cocktails")
