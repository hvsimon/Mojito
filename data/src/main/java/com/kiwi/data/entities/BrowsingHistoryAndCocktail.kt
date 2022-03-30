package com.kiwi.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class BrowsingHistoryAndCocktail(
    @Embedded
    val browsingHistoryEntity: BrowsingHistoryEntity,

    @Relation(
        parentColumn = "cocktail_id",
        entityColumn = "idDrink"
    )
    val cocktail: FullDrinkEntity
)
