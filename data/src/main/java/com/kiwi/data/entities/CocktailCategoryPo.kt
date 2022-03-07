package com.kiwi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CocktailCategoryPo(
    @PrimaryKey
    val name: String,
)
