package com.kiwi.data.mapper

import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.FullDrinkDto
import com.kiwi.data.entities.Ingredient
import com.kiwi.data.entities.SimpleDrinkDto

fun FullDrinkDto.toCocktailPo(): CocktailPo {
    return CocktailPo(
        cocktailId = this.id,
        name = this.drink,
        gallery = listOf(this.thumb),
        tags = this.tags,
        category = this.category,
        iba = this.iba,
        alcoholic = this.alcoholic,
        glass = this.glass,
        ingredients = this.ingredients
            .filter { it.isNotBlank() }
            .mapIndexed { index, s ->
                Ingredient(
                    name = s,
                    amount = this.measures.getOrElse(index) { "" }
                )
            },
        steps = this.instructions,
    )
}

fun SimpleDrinkDto.toCocktailPo(): CocktailPo {
    return CocktailPo(
        cocktailId = this.id,
        name = this.drink,
        gallery = listOf(this.thumb),
        category = "",
        alcoholic = "",
        glass = "",
        ingredients = listOf(),
        steps = "",
    )
}
