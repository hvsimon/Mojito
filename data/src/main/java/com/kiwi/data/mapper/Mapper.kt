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
        category = category,
        ingredients = this.ingredients.zip(this.measures) { ingredient, measure ->
            Ingredient(
                name = ingredient,
                amount = measure,
            )
        },
        steps = listOf(this.instructions),
    )
}

fun SimpleDrinkDto.toCocktailPo(): CocktailPo {
    return CocktailPo(
        cocktailId = this.id,
        name = this.drink,
        gallery = listOf(this.thumb),
        category = "",
        ingredients = listOf(),
        steps = listOf(),
    )
}
