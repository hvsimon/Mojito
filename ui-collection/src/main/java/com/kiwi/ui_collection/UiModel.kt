package com.kiwi.ui_collection

import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.FollowedRecipe

sealed class UiModel {
    class HeaderModel(val name: String) : UiModel()
    class FollowedModel(val followedRecipe: FollowedRecipe, val cocktail: CocktailPo) : UiModel()
}
