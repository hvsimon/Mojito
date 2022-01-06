package com.kiwi.ui_onboarding

import com.kiwi.data.entities.BaseWine
import com.kiwi.data.entities.Cocktail

data class OnboardingUiState(
    val coverCocktail: Cocktail? = null,
    val baseWines: List<BaseWine> = emptyList()
)

