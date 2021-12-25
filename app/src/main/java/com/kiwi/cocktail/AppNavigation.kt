package com.kiwi.cocktail

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kiwi.cocktail.ui.Watched
import com.kiwi.ui_collection.Collection
import com.kiwi.ui_onboarding.Onboarding
import com.kiwi.ui_recipe.Recipe

internal sealed class Screen(
    val route: String,
    @StringRes val labelResId: Int,
) {
    object Onboarding : Screen(
        "onboarding",
        R.string.onboarding_title,
    )

    object Collection : Screen(
        "collection",
        R.string.collection_title,
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
        startDestination = Screen.Onboarding.route,
        modifier = modifier
    ) {
        composable(Screen.Onboarding.route) {
            Onboarding(
                openRecipe = { navController.navigate(Screen.Recipe.route) },
                {},
            )
        }
        composable(Screen.Collection.route) { Collection() }
        composable(Screen.Watched.route) { Watched() }

        composable(Screen.Recipe.route) { Recipe() }
    }
}
