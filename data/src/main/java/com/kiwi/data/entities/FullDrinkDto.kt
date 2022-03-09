package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullDrinkDto(
    @SerialName("idDrink") val id: String,
    @SerialName("strDrink") val drink: String,
    @SerialName("strCategory") val category: String,
    @SerialName("strInstructions") val instructions: String,
    @SerialName("strDrinkThumb") val thumb: String,

    @SerialName("strIngredient1") private val _ingredient1: String?,
    @SerialName("strIngredient2") private val _ingredient2: String?,
    @SerialName("strIngredient3") private val _ingredient3: String?,
    @SerialName("strIngredient4") private val _ingredient4: String?,
    @SerialName("strIngredient5") private val _ingredient5: String?,
    @SerialName("strIngredient6") private val _ingredient6: String?,
    @SerialName("strIngredient7") private val _ingredient7: String?,
    @SerialName("strIngredient8") private val _ingredient8: String?,
    @SerialName("strIngredient9") private val _ingredient9: String?,
    @SerialName("strIngredient10") private val _ingredient10: String?,
    @SerialName("strIngredient11") private val _ingredient11: String?,
    @SerialName("strIngredient12") private val _ingredient12: String?,
    @SerialName("strIngredient13") private val _ingredient13: String?,
    @SerialName("strIngredient14") private val _ingredient14: String?,
    @SerialName("strIngredient15") private val _ingredient15: String?,

    @SerialName("strMeasure2") private val _measure2: String?,
    @SerialName("strMeasure3") private val _measure3: String?,
    @SerialName("strMeasure1") private val _measure1: String?,
    @SerialName("strMeasure4") private val _measure4: String?,
    @SerialName("strMeasure5") private val _measure5: String?,
    @SerialName("strMeasure6") private val _measure6: String?,
    @SerialName("strMeasure7") private val _measure7: String?,
    @SerialName("strMeasure8") private val _measure8: String?,
    @SerialName("strMeasure9") private val _measure9: String?,
    @SerialName("strMeasure10") private val _measure10: String?,
    @SerialName("strMeasure11") private val _measure11: String?,
    @SerialName("strMeasure12") private val _measure12: String?,
    @SerialName("strMeasure13") private val _measure13: String?,
    @SerialName("strMeasure14") private val _measure14: String?,
    @SerialName("strMeasure15") private val _measure15: String?,
) {

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
