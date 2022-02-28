package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "catalog_name")
    val catalogName: String,

    @ColumnInfo(name = "cocktail_id")
    val cocktailId: String,
)

data class FavoriteAndCocktail(
    @Embedded
    val favorite: Favorite,

    @Relation(
        parentColumn = "cocktail_id",
        entityColumn = "cocktail_id"
    )
    val cocktail: CocktailPo
)
