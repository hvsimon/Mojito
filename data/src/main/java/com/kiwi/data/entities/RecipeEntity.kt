package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable

@Entity
data class Cocktail(
    @PrimaryKey
    val cocktailId: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "gallery")
    val gallery: List<String>,

    @ColumnInfo(name = "intro")
    val intro: String,

    @ColumnInfo(name = "steps")
    val steps: List<String>,

    @ColumnInfo(name = "tips")
    val tipsAndTricks: Set<String>,
)

@Serializable
@Entity
data class Ingredient(
    @PrimaryKey
    val ingredientId: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "amount")
    val amount: String,
)

@Entity(primaryKeys = ["cocktailId", "ingredientId"])
data class CocktailIngredientCrossRef(
    val cocktailId: Long,
    val ingredientId: Long
)

data class RecipeEntity(
    @Embedded val cocktail: Cocktail,
    @Relation(
        parentColumn = "cocktailId",
        entityColumn = "ingredientId",
        associateBy = Junction(CocktailIngredientCrossRef::class)
    )
    val ingredients: List<Ingredient>
)
