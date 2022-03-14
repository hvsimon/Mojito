package com.kiwi.cocktail

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.kiwi.ui_about.About
import com.kiwi.ui_about.Licenses
import com.kiwi.ui_cocktail_list.CocktailList
import com.kiwi.ui_collection.Collection
import com.kiwi.ui_ingredient.Ingredient
import com.kiwi.ui_onboarding.Onboarding
import com.kiwi.ui_recipe.Recipe
import com.kiwi.ui_search.Search

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

    object About : Screen(
        "about",
        R.string.about_title,
    )

    object Recipe : Screen(
        "recipe/{cocktailId}",
        R.string.recipe_title,
    )

    object CocktailList : Screen(
        "cocktail_list/?base_liquor_type={base_liquor_type}&iba_category_type={iba_category_type}",
        R.string.cocktail_list_title,
    )

    object Search : Screen(
        "search",
        R.string.search,
    )

    object Licenses : Screen(
        "licenses",
        R.string.licenses,
    )

    object Ingredient : Screen(
        "ingredient/{ingredientName}",
        R.string.ingredient_title,
    )
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Onboarding.route,
            modifier = modifier,
        ) {
            composable(Screen.Onboarding.route) {
                Onboarding(
                    openSearch = {
                        navController.navigate("search")
                    },
                    openRecipe = { cocktailId ->
                        navController.navigate("recipe/$cocktailId")
                    },
                    openCocktailList = { baseLiquorType, ibaCategoryType ->
                        navController.navigate(
                            "cocktail_list/" +
                                "?base_liquor_type=${baseLiquorType?.name ?: ""}" +
                                "&iba_category_type=${ibaCategoryType?.name ?: ""}"
                        )
                    },
                    openIngredient = { ingredientName ->
                        navController.navigate("ingredient/$ingredientName")
                    }
                )
            }

            composable(Screen.Collection.route) {
                Collection(
                    openRecipe = { cocktailId ->
                        navController.navigate("recipe/$cocktailId")
                    },
                    openSearch = {
                        navController.navigate("search")
                    },
                )
            }

            composable(Screen.About.route) {
                About(
                    openLicenses = { navController.navigate("licenses") }
                )
            }

            composable(
                route = Screen.Recipe.route,
                arguments = listOf(
                    navArgument("cocktailId") {
                        type = NavType.StringType
                    }
                )
            ) { Recipe() }

            composable(
                route = Screen.CocktailList.route,
                arguments = listOf(
                    navArgument("base_liquor_type") {
                        type = NavType.StringType
                        nullable = true
                    },
                    navArgument("iba_category_type") {
                        type = NavType.StringType
                        nullable = true
                    },
                )
            ) {
                CocktailList(
                    navigateUp = navController::navigateUp,
                    openRecipe = { cocktailId ->
                        navController.navigate("recipe/$cocktailId")
                    },
                )
            }

            composable(Screen.Search.route) {
                Search(
                    navigateUp = navController::navigateUp,
                    openRecipe = { cocktailId ->
                        navController.navigate("recipe/$cocktailId")
                    },
                )
            }

            composable(Screen.Licenses.route) {
                Licenses(
                    navigateUp = navController::navigateUp
                )
            }

            bottomSheet(Screen.Ingredient.route) {
                Ingredient()
            }
        }
    }
}
