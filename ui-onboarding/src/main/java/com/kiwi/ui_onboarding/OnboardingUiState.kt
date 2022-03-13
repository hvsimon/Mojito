package com.kiwi.ui_onboarding

import com.kiwi.data.entities.BaseLiquorGroup
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.IBACategory

data class OnboardingUiState(
    val coverCocktail: CocktailPo? = null,
    val baseWineGroups: List<BaseLiquorGroup> = emptyList(),
    val ibaCategories: List<IBACategory> = emptyList(),
    val isRefreshing: Boolean = false
)
