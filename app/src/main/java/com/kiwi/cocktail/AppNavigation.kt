package com.kiwi.cocktail

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kiwi.cocktail.ui.Watched
import com.kiwi.ui_cocktail_list.CocktailList
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
        "recipe/{cocktailId}",
        R.string.recipe_title,
    )

    object CocktailList : Screen(
        "cocktail_list/{ingredient}",
        R.string.cocktail_list_title,
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
        modifier = modifier,
    ) {
        composable(Screen.Onboarding.route) {
            Onboarding(
                openRecipe = { cocktailId ->
                    navController.navigate("recipe/$cocktailId")
                },
                openCocktailList = { ingredient ->
                    navController.navigate("cocktail_list/$ingredient")
                },
            )
        }

        composable(Screen.Collection.route) {
            Collection(
                openRecipe = { cocktailId ->
                    navController.navigate("recipe/$cocktailId")
                },
            )
        }

        composable(Screen.Watched.route) { Watched() }

        composable(
            route = Screen.Recipe.route,
            arguments = listOf(
                navArgument("cocktailId") {
                    type = NavType.StringType
                }
            )
        ) { Recipe() }

        composable(Screen.CocktailList.route) {
            CocktailList(
                navigateUp = navController::navigateUp,
                openRecipe = { cocktailId ->
                    navController.navigate("recipe/$cocktailId")
                },
            )
        }
    }
}
