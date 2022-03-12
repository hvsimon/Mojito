package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    indices = [
        Index(value = ["iba"], unique = false)
    ]
)
data class CocktailPo(
    @PrimaryKey
    @ColumnInfo(name = "cocktail_id") val cocktailId: String,

    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "gallery") val gallery: List<String>,
    @ColumnInfo(name = "tags") val tags: String? = null,
    @ColumnInfo(name = "category") val category: String,
    @ColumnInfo(name = "iba") val iba: String? = null,
    @ColumnInfo(name = "alcoholic") val alcoholic: String,
    @ColumnInfo(name = "glass") val glass: String,
    @ColumnInfo(name = "ingredients") val ingredients: List<Ingredient>,
    @ColumnInfo(name = "steps") val steps: String
)
