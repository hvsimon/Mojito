package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class CocktailPo(

    @PrimaryKey
    @ColumnInfo(name = "cocktail_id")
    val cocktailId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "gallery")
    val gallery: List<String>,

    @ColumnInfo(name = "ingredients")
    val ingredients: List<Ingredient>,

    @ColumnInfo(name = "steps")
    val steps: List<String>,
)
