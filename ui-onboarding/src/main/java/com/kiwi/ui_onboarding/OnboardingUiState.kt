package com.kiwi.ui_onboarding

import androidx.annotation.StringRes
import com.kiwi.data.entities.BaseLiquorType
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.IBACategoryType

data class OnboardingUiState(
    val coverCocktail: CocktailPo? = null,
    val baseLiquorItemUiStates: List<BaseLiquorItemUiState> = listOf(),
    val ibaCategoryUiStates: List<IBACategoryItemUiState> = listOf(),
    val isRefreshing: Boolean = false
)

data class BaseLiquorItemUiState(
    val type: BaseLiquorType,
) {
    @StringRes
    val displayTextResId: Int = when (type) {
        BaseLiquorType.RUM -> R.string.rum
        BaseLiquorType.GIN -> R.string.gin
        BaseLiquorType.VODKA -> R.string.vodka
        BaseLiquorType.TEQUILA -> R.string.tequila
        BaseLiquorType.WHISKEY -> R.string.whiskey
        BaseLiquorType.BRANDY -> R.string.brandy
    }

    val imageUrl: String = when (type) {
        BaseLiquorType.RUM -> "https://images.unsplash.com/" +
            "photo-1614313511387-1436a4480ebb" +
            "?ixlib=rb-1.2.1" +
            "&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8" +
            "&auto=format" +
            "&fit=crop" +
            "&w=1760" +
            "&q=80"
        BaseLiquorType.GIN -> "https://images.unsplash.com/" +
            "photo-1608885898957-a559228e8749" +
            "?ixlib=rb-1.2.1" +
            "&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8" +
            "&auto=format" +
            "&fit=crop" +
            "&w=987" +
            "&q=80"
        BaseLiquorType.VODKA -> "https://images.unsplash.com/" +
            "photo-1550985543-f47f38aeee65" +
            "?ixlib=rb-1.2.1" +
            "&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8" +
            "&auto=format" +
            "&fit=crop" +
            "&w=1335" +
            "&q=80"
        BaseLiquorType.TEQUILA -> "https://images.unsplash.com/" +
            "photo-1516535794938-6063878f08cc" +
            "?ixlib=rb-1.2.1" +
            "&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8" +
            "&auto=format" +
            "&fit=crop" +
            "&w=988" +
            "&q=80"
        BaseLiquorType.WHISKEY -> "https://images.unsplash.com" +
            "/photo-1527281400683-1aae777175f8" +
            "?ixlib=rb-1.2.1" +
            "&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8" +
            "&auto=format" +
            "&fit=crop" +
            "&w=987" +
            "&q=80"
        BaseLiquorType.BRANDY -> "https://images.unsplash.com/" +
            "photo-1619451050621-83cb7aada2d7" +
            "?ixlib=rb-1.2.1" +
            "&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8" +
            "&auto=format" +
            "&fit=crop" +
            "&w=986" +
            "&q=80"
    }
}

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