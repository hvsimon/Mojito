package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class BaseLiquorType {

    @SerialName("Rum")
    RUM,

    @SerialName("Gin")
    GIN,

    @SerialName("Vodka")
    VODKA,

    @SerialName("Tequila")
    TEQUILA,

    @SerialName("Whiskey")
    WHISKEY,

    @SerialName("Brandy")
    BRANDY,
}
