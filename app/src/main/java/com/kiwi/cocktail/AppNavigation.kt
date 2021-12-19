package com.kiwi.cocktail

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kiwi.cocktail.ui.Following
import com.kiwi.cocktail.ui.Onboarding
import com.kiwi.cocktail.ui.Recipe
import com.kiwi.cocktail.ui.Watched

internal sealed class Screen(
    val route: String,
    @StringRes val labelResId: Int,
) {
    object Onboarding : Screen(
        "onboarding",
        R.string.onboarding_title,
    )

    object Following : Screen(
        "following",
        R.string.following_shows_title,
    )

    object Watched : Screen(
        "watched",
        R.string.watched_shows_title,
    )

    object Recipe : Screen(
        "recipe",
        R.string.recipe_title,
    )
}

@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Recipe.route,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) { Onboarding() }
        composable(Screen.Following.route) { Following() }
        composable(Screen.Watched.route) { Watched() }

        composable(Screen.Recipe.route) { Recipe() }
    }
}
