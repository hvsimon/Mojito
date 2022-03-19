package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class FullIngredientEntity(

    @PrimaryKey
    @ColumnInfo(name = "idIngredient")
    @SerialName("idIngredient")
    val id: String,

    @ColumnInfo(name = "strIngredient")
    @SerialName("strIngredient")
    val name: String,

    @ColumnInfo(name = "strDescription")
    @SerialName("strDescription")
    val description: String?,

    @ColumnInfo(name = "strType")
    @SerialName("strType")
    val type: String?,
)
