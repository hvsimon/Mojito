package com.kiwi.ui_onboarding

import com.kiwi.data.entities.BaseWine
import com.kiwi.data.entities.CocktailPo

data class OnboardingUiState(
    val coverCocktail: CocktailPo? = null,
    val baseWines: List<BaseWine> = emptyList()
)

