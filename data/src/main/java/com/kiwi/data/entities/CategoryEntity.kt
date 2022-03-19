package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "strCategory")
    @SerialName("strCategory")
    val name: String,
)
