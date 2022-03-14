package com.kiwi.data

import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.FullDrinkDto
import com.kiwi.data.entities.Ingredient

object TestData {

    val cocktailPo1 = CocktailPo(
        cocktailId = "1",
        name = "Cocktail Name",
        gallery = listOf("http://"),
        category = "Category",
        alcoholic = "Alcoholic",
        glass = "Glass",
        ingredients = listOf(
            Ingredient(
                name = "\uD83C\uDF78 白蘭姆酒",
                amount = "2 shots"
            ),
            Ingredient(
                name = "\uD83E\uDDC2 糖漿",
                amount = "0.5 shot"
            )
        ),
        steps = "Pour all ingredients into a cocktail shaker",
    )

    val fullDrinkDto1 = FullDrinkDto(
        id = "1",
        drink = "Cocktail Name",
        tags = null,
        category = "Category",
        iba = null,
        alcoholic = "Alcoholic",
        glass = "Glass",
        instructions = "Pour all ingredients into a cocktail shaker",
        thumb = "http://",

        _ingredient1 = "\uD83C\uDF78 白蘭姆酒",
        _ingredient2 = "\uD83E\uDDC2 糖漿",
        _ingredient3 = null,
        _ingredient4 = null,
        _ingredient5 = null,
        _ingredient6 = null,
        _ingredient7 = null,
        _ingredient8 = null,
        _ingredient9 = null,
        _ingredient10 = null,
        _ingredient11 = null,
        _ingredient12 = null,
        _ingredient13 = null,
        _ingredient14 = null,
        _ingredient15 = null,

        _measure2 = "2 shots",
        _measure3 = "0.5 shot",
        _measure1 = null,
        _measure4 = null,
        _measure5 = null,
        _measure6 = null,
        _measure7 = null,
        _measure8 = null,
        _measure9 = null,
        _measure10 = null,
        _measure11 = null,
        _measure12 = null,
        _measure13 = null,
        _measure14 = null,
        _measure15 = null,
    )
}