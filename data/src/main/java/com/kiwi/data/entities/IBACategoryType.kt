package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class IBACategoryType {

    @SerialName("The Unforgettables")
    THE_UNFORGETTABLES,

    @SerialName("Contemporary Classics")
    CONTEMPORARY_CLASSICS,

    @SerialName("New Era Drinks")
    NEW_ERA_DRINKS,
}
