package com.kiwi.data.entities

data class BaseLiquorGroup(
    val groupName: String,
    val groupImageUrl: String,
    val baseLiquorList: List<BaseLiquor>,
)
