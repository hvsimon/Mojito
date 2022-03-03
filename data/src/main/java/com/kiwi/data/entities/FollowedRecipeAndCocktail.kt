package com.kiwi.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class FollowedRecipeAndCocktail(
    @Embedded
    val followedRecipe: FollowedRecipe,

    @Relation(
        parentColumn = "cocktail_id",
        entityColumn = "cocktail_id"
    )
    val cocktail: CocktailPo
)
