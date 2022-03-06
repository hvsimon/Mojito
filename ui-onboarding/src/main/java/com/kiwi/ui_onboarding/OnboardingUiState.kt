package com.kiwi.ui_onboarding

import com.kiwi.data.entities.BaseWineGroup
import com.kiwi.data.entities.Category
import com.kiwi.data.entities.CocktailPo

data class OnboardingUiState(
    val coverCocktail: CocktailPo? = null,
    val baseWineGroups: List<BaseWineGroup> = emptyList(),
    val categories: List<Category> = emptyList(),
)
