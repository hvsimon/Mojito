package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cocktail(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "gallery")
    val gallery: List<String>,

    @ColumnInfo(name = "intro")
    val intro: String,

    @ColumnInfo(name = "ingredients")
    val ingredients: List<Ingredient>,

    @ColumnInfo(name = "steps")
    val steps: List<String>,

    @ColumnInfo(name = "tips")
    val tipsAndTricks: Set<String>,
)
