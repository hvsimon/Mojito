package com.kiwi.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseWineGroup(
    @SerialName("group_name") val groupName: String,
    @SerialName("group_image_url") val groupImageUrl: String,
    @SerialName("base_wine_list") val baseWineList: List<BaseWine>,
)
