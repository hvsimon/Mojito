package com.kiwi.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryPo(
    @PrimaryKey
    val name: String,
)
