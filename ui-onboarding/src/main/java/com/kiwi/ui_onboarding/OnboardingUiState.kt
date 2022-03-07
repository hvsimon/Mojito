package com.kiwi.ui_onboarding

import com.kiwi.data.entities.BaseWineGroup
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.IBACategory

data class OnboardingUiState(
    val coverCocktail: CocktailPo? = null,
    val baseWineGroups: List<BaseWineGroup> = emptyList(),
    val ibaCategories: List<IBACategory> = emptyList(),
)
