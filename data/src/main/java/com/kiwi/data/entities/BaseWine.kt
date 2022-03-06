package com.kiwi.data.entities

import kotlinx.serialization.Serializable

@Serializable
data class BaseWine(
    val id: String,
    val name: String,
)
