package com.kiwi.ui_collection

import com.kiwi.data.entities.FollowedRecipe
import com.kiwi.data.entities.FullDrinkEntity

sealed class UiModel {
    class HeaderModel(val name: String) : UiModel()
    class FollowedModel(val followedRecipe: FollowedRecipe, val cocktail: FullDrinkEntity) : UiModel()
}
