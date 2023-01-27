package com.kiwi.cocktail.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.kiwi.base.utils.Analytics
import com.kiwi.cocktail.AppNavigation
import com.kiwi.cocktail.R
import com.kiwi.cocktail.Screen

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialNavigationApi::class,
)
@Composable
fun Home(
    analytics: Analytics,
) {

    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)

    // Launch an effect to track changes to the current back stack entry, and push them
    // as a screen views to analytics
    LaunchedEffect(navController, analytics) {
        navController.currentBackStackEntryFlow.collect { entry ->
            analytics.trackScreenView(
                route = entry.destination.route,
                arguments = entry.arguments,
            )
        }
    }

    val mainScreen = listOf(
        Screen.Explore.route,
        Screen.Collection.route,
        Screen.About.route,
    )
    val showBottomBar = navController
        .currentBackStackEntryAsState().value?.destination?.route in mainScreen

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomBar,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                content = {
                    HomeBottomNavigation(navController)
                }
            )
        },
    ) { paddingValues ->
        val paddingBottom = if (showBottomBar) paddingValues.calculateBottomPadding() else 0.dp
        AppNavigation(
            navController = navController,
            bottomSheetNavigator = bottomSheetNavigator,
            modifier = Modifier.padding(bottom = paddingBottom),
        )
    }
}

@Composable
fun HomeBottomNavigation(
    navController: NavHostController,
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        HomeNavigationItems.forEach { item ->
            val selected =
                currentDestination?.hierarchy?.any { it.route == item.screen.route } == true
            val iconRes = if (selected) item.selectedIconResId else item.iconResId
            NavigationBarItem(
                icon = { Icon(painterResource(id = iconRes), contentDescription = null) },
                label = { Text(text = stringResource(id = item.labelResId)) },
                selected = selected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

private data class HomeNavigationItem(
    val screen: Screen,
    @StringRes val labelResId: Int,
    @DrawableRes val iconResId: Int,
    @DrawableRes val selectedIconResId: Int,
)

private val HomeNavigationItems = listOf(
    HomeNavigationItem(
        screen = Screen.Explore,
        labelResId = R.string.explore_title,
        iconResId = R.drawable.ic_liquor_outlined,
        selectedIconResId = R.drawable.ic_liquor_filled,
    ),
    HomeNavigationItem(
        screen = Screen.Collection,
        labelResId = R.string.collection_title,
        iconResId = R.drawable.ic_favorite_outlined,
        selectedIconResId = R.drawable.ic_favorite_filled,
    ),
    HomeNavigationItem(
        screen = Screen.About,
        labelResId = R.string.about_title,
        iconResId = R.drawable.ic_person_outline,
        selectedIconResId = R.drawable.ic_person_filled,
    )
)
