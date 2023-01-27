package com.kiwi.cocktail

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.kiwi.ui_browsing_history.BrowsingHistory
import com.kiwi.ui_cocktail_list.CocktailList
import com.kiwi.ui_cocktail_list.CocktailListType
import com.kiwi.ui_collection.Collection
import com.kiwi.ui_explore.Explore
import com.kiwi.ui_ingredient.Ingredient
import com.kiwi.ui_recipe.Recipe
import com.kiwi.ui_search.Search

internal sealed class Screen(val route: String) {
    object Explore : Screen("explore")
    object Collection : Screen("collection")
    object About : Screen("about")
    object Recipe : Screen("recipe/{cocktailId}")
    object CocktailList : Screen("cocktail_list/{type}/?keyword={keyword}")

    object Search : Screen("search")
    object Licenses : Screen("licenses")
    object Ingredient : Screen("ingredient/{ingredientName}")
    object BrowsingHistory : Screen("browsing_history")
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
        sheetShape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.statusBarsPadding(),
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Explore.route,
            modifier = modifier,
        ) {
            composable(Screen.Explore.route) {
                Explore(
                    openSearch = {
                        navController.navigate("search")
                    },
                    openRecipe = { cocktailId ->
                        navController.navigate("recipe/$cocktailId")
                    },
                    openCocktailListWithBaseLiquor = { baseLiquorType ->
                        navController.navigate(
                            "cocktail_list/${CocktailListType.BASE_LIQUOR}/" +
                                "?keyword=$baseLiquorType"
                        )
                    },
                    openCocktailListWithIBACategory = { ibaCategoryType ->
                        navController.navigate(
                            "cocktail_list/${CocktailListType.IBA_CATEGORY}/" +
                                "?keyword=$ibaCategoryType"
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
                    openBrowsingHistory = { navController.navigate("browsing_history") },
                    openLicenses = { navController.navigate("licenses") },
                )
            }

            composable(
                route = Screen.Recipe.route,
                arguments = listOf(
                    navArgument("cocktailId") {
                        type = NavType.StringType
                    }
                )
            ) {
                Recipe(
                    openIngredient = { ingredientName ->
                        navController.navigate("ingredient/$ingredientName")
                    },
                    openRecipe = { cocktailId ->
                        navController.navigate("recipe/$cocktailId")
                    }
                )
            }

            composable(
                route = Screen.CocktailList.route,
                arguments = listOf(
                    navArgument("type") {
                        type = NavType.EnumType(CocktailListType::class.java)
                    },
                    navArgument("keyword") {
                        type = NavType.StringType
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
                    openCocktailListWithCategory = { categoryName ->
                        navController.navigate(
                            "cocktail_list/${CocktailListType.CATEGORY}/" +
                                "?keyword=$categoryName"
                        )
                    },
                    openCocktailListWithFirstLetter = { firstLetter ->
                        navController.navigate(
                            "cocktail_list/${CocktailListType.FIRST_LETTER}/" +
                                "?keyword=$firstLetter"
                        )
                    },
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
                Ingredient(
                    openRecipe = { cocktailId ->
                        navController.navigate("recipe/$cocktailId")
                    },
                )
            }

            composable(Screen.BrowsingHistory.route) {
                BrowsingHistory(
                    navigateUp = navController::navigateUp,
                    openRecipe = { cocktailId ->
                        navController.navigate("recipe/$cocktailId")
                    },
                )
            }
        }
    }
}
