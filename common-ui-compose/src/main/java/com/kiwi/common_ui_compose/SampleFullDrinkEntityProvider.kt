package com.kiwi.common_ui_compose

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.kiwi.data.entities.FullDrinkEntity

class SampleFullDrinkEntityProvider : PreviewParameterProvider<FullDrinkEntity> {

    override val values: Sequence<FullDrinkEntity> = sequenceOf(
        FullDrinkEntity(
            id = "id",
            name = "name",
            tags = null,
            category = "category",
            iba = "iba",
            alcoholic = "alcoholic",
            glass = "glass",
            instructions = "instructions",
            thumb = "thumb",
            _ingredient1 = "_ingredient1",
            _ingredient2 = "_ingredient2",
            _ingredient3 = null,
            _ingredient4 = null,
            _ingredient5 = null,
            _ingredient6 = null,
            _ingredient7 = null,
            _ingredient8 = null,
            _ingredient9 = null,
            _ingredient10 = null,
            _ingredient11 = null,
            _ingredient12 = null,
            _ingredient13 = null,
            _ingredient14 = null,
            _ingredient15 = null,
            _measure1 = "_measure1",
            _measure2 = "_measure2",
            _measure3 = null,
            _measure4 = null,
            _measure5 = null,
            _measure6 = null,
            _measure7 = null,
            _measure8 = null,
            _measure9 = null,
            _measure10 = null,
            _measure11 = null,
            _measure12 = null,
            _measure13 = null,
            _measure14 = null,
            _measure15 = null,
        )
    )
}