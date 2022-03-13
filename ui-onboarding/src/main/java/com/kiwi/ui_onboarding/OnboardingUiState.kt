package com.kiwi.ui_onboarding

import androidx.annotation.StringRes
import com.kiwi.data.entities.BaseLiquorGroup
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.IBACategoryType

data class OnboardingUiState(
    val coverCocktail: CocktailPo? = null,
    val baseWineGroups: List<BaseLiquorGroup> = emptyList(),
    val ibaCategoryUiStates: List<IBACategoryItemUiState> = emptyList(),
    val isRefreshing: Boolean = false
)

data class IBACategoryItemUiState(
    val type: IBACategoryType,
) {
    @StringRes
    val displayTextResId: Int = when (type) {
        IBACategoryType.THE_UNFORGETTABLES -> R.string.the_unforgettables
        IBACategoryType.CONTEMPORARY_CLASSICS -> R.string.contemporary_classics
        IBACategoryType.NEW_ERA_DRINKS -> R.string.new_era_drinks
    }

    val imageUrl: String = when (type) {
        IBACategoryType.THE_UNFORGETTABLES -> "https://images.unsplash.com/" +
            "photo-1574056067299-a11c5b576e69" +
            "?ixlib=rb-1.2.1" +
            "&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8" +
            "&auto=format" +
            "&fit=crop" +
            "&w=2148&q=80"
        IBACategoryType.CONTEMPORARY_CLASSICS -> "https://images.unsplash.com/" +
            "photo-1597290282695-edc43d0e7129" +
            "?ixlib=rb-1.2.1" +
            "&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8" +
            "&auto=format" +
            "&fit=crop" +
            "&w=2375" +
            "&q=80"
        IBACategoryType.NEW_ERA_DRINKS -> "https://images.unsplash.com/" +
            "photo-1553484604-9f524520c793" +
            "?ixlib=rb-1.2.1" +
            "&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8" +
            "&auto=format&fit=crop&w=2378&q=80"
    }
}