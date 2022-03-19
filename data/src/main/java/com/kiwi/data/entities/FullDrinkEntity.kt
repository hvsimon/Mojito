package com.kiwi.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class FullDrinkEntity(
    @PrimaryKey
    @ColumnInfo(name = "idDrink", index = true)
    @SerialName("idDrink")
    val id: String,

    @ColumnInfo(name = "strDrink")
    @SerialName("strDrink")
    val name: String,

    @ColumnInfo(name = "strTags")
    @SerialName("strTags")
    val tags: String?,

    @ColumnInfo(name = "strCategory")
    @SerialName("strCategory")
    val category: String,

    @ColumnInfo(name = "strIBA", index = true)
    @SerialName("strIBA")
    val iba: String?,

    @ColumnInfo(name = "strAlcoholic")
    @SerialName("strAlcoholic")
    val alcoholic: String,

    @ColumnInfo(name = "strGlass")
    @SerialName("strGlass")
    val glass: String,

    @ColumnInfo(name = "strInstructions")
    @SerialName("strInstructions")
    val instructions: String,

    @ColumnInfo(name = "strDrinkThumb")
    @SerialName("strDrinkThumb")
    val thumb: String,

    @ColumnInfo(name = "strIngredient1")
    @SerialName("strIngredient1")
    val _ingredient1: String?,

    @ColumnInfo(name = "strIngredient2")
    @SerialName("strIngredient2")
    val _ingredient2: String?,

    @ColumnInfo(name = "strIngredient3")
    @SerialName("strIngredient3")
    val _ingredient3: String?,

    @ColumnInfo(name = "strIngredient4")
    @SerialName("strIngredient4")
    val _ingredient4: String?,

    @ColumnInfo(name = "strIngredient5")
    @SerialName("strIngredient5")
    val _ingredient5: String?,

    @ColumnInfo(name = "strIngredient6")
    @SerialName("strIngredient6")
    val _ingredient6: String?,

    @ColumnInfo(name = "strIngredient7")
    @SerialName("strIngredient7")
    val _ingredient7: String?,

    @ColumnInfo(name = "strIngredient8")
    @SerialName("strIngredient8")
    val _ingredient8: String?,

    @ColumnInfo(name = "strIngredient9")
    @SerialName("strIngredient9")
    val _ingredient9: String?,

    @ColumnInfo(name = "strIngredient10")
    @SerialName("strIngredient10")
    val _ingredient10: String?,

    @ColumnInfo(name = "strIngredient11")
    @SerialName("strIngredient11")
    val _ingredient11: String?,

    @ColumnInfo(name = "strIngredient12")
    @SerialName("strIngredient12")
    val _ingredient12: String?,

    @ColumnInfo(name = "strIngredient13")
    @SerialName("strIngredient13")
    val _ingredient13: String?,

    @ColumnInfo(name = "strIngredient14")
    @SerialName("strIngredient14")
    val _ingredient14: String?,

    @ColumnInfo(name = "strIngredient15")
    @SerialName("strIngredient15")
    val _ingredient15: String?,

    @ColumnInfo(name = "strMeasure1")
    @SerialName("strMeasure1")
    val _measure1: String?,

    @ColumnInfo(name = "strMeasure2")
    @SerialName("strMeasure2")
    val _measure2: String?,

    @ColumnInfo(name = "strMeasure3")
    @SerialName("strMeasure3")
    val _measure3: String?,

    @ColumnInfo(name = "strMeasure4")
    @SerialName("strMeasure4")
    val _measure4: String?,

    @ColumnInfo(name = "strMeasure5")
    @SerialName("strMeasure5")
    val _measure5: String?,

    @ColumnInfo(name = "strMeasure6")
    @SerialName("strMeasure6")
    val _measure6: String?,

    @ColumnInfo(name = "strMeasure7")
    @SerialName("strMeasure7")
    val _measure7: String?,

    @ColumnInfo(name = "strMeasure8")
    @SerialName("strMeasure8")
    val _measure8: String?,

    @ColumnInfo(name = "strMeasure9")
    @SerialName("strMeasure9")
    val _measure9: String?,

    @ColumnInfo(name = "strMeasure10")
    @SerialName("strMeasure10")
    val _measure10: String?,

    @ColumnInfo(name = "strMeasure11")
    @SerialName("strMeasure11")
    val _measure11: String?,

    @ColumnInfo(name = "strMeasure12")
    @SerialName("strMeasure12")
    val _measure12: String?,

    @ColumnInfo(name = "strMeasure13")
    @SerialName("strMeasure13")
    val _measure13: String?,

    @ColumnInfo(name = "strMeasure14")
    @SerialName("strMeasure14")
    val _measure14: String?,

    @ColumnInfo(name = "strMeasure15")
    @SerialName("strMeasure15")
    val _measure15: String?,
) {

    @Ignore
    val ingredients: List<String> = listOfNotNull(
        _ingredient1,
        _ingredient2,
        _ingredient3,
        _ingredient4,
        _ingredient5,
        _ingredient6,
        _ingredient7,
        _ingredient8,
        _ingredient9,
        _ingredient10,
        _ingredient11,
        _ingredient12,
        _ingredient13,
        _ingredient14,
        _ingredient15,
    )

    @Ignore
    val measures: List<String> = listOfNotNull(
        _measure1,
        _measure2,
        _measure3,
        _measure4,
        _measure5,
        _measure6,
        _measure7,
        _measure8,
        _measure9,
        _measure10,
        _measure11,
        _measure12,
        _measure13,
        _measure14,
        _measure15,
    )
}
