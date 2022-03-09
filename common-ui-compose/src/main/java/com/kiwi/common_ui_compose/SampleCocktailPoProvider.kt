package com.kiwi.common_ui_compose

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.Ingredient

class SampleCocktailPoProvider : PreviewParameterProvider<CocktailPo> {

    override val values: Sequence<CocktailPo> = sequenceOf(
        CocktailPo(
            cocktailId = "",
            name = "Cocktail Name",
            gallery = listOf(),
            category = "category",
            ingredients = listOf(
                Ingredient(
                    name = "\uD83C\uDF78 白蘭姆酒",
                    amount = "2 shots"
                ),
                Ingredient(
                    name = "\uD83E\uDDC2 糖漿",
                    amount = "0.5 shot"
                ),
                Ingredient(
                    name = "\uD83C\uDF4B 萊姆片",
                    amount = "4"
                ),
                Ingredient(
                    name = "\uD83C\uDF3F 新鮮薄荷",
                    amount = "12 leaves"
                ),
                Ingredient(
                    name = "\uD83E\uDD64 蘇打水",
                    amount = "fill to top"
                ),
            ),
            steps = listOf(
                "Pour all ingredients into a cocktail shaker, "
            ),
        )
    )
}