package com.kiwi.ui_collection

import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Favorite

sealed class UiModel {
    class HeaderModel(val name: String) : UiModel()
    class FavoriteModel(val favorite: Favorite, val cocktail: Cocktail) : UiModel()
}