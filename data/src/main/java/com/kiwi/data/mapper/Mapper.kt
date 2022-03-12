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
        ingredients = this.ingredients.zip(this.measures) { ingredient, measure ->
            Ingredient(
                name = ingredient,
                amount = measure,
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
