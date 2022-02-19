package com.kiwi.cocktail.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.kiwi.cocktail.AppNavigation
import com.kiwi.cocktail.R
import com.kiwi.cocktail.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Home() {
    val navController = rememberNavController()
    val mainScreen = listOf(
        Screen.Onboarding.route,
        Screen.Collection.route,
        Screen.Watched.route,
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
    ) { innerPadding ->
        val paddingBottom = if (showBottomBar) innerPadding else PaddingValues(0.dp)
        AppNavigation(navController = navController, modifier = Modifier.padding(paddingBottom))
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
            // <div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
            val iconRes = if (selected) item.selectedIconResId else item.iconResId
            NavigationBarItem(
                icon = { Icon(painterResource(id = iconRes), contentDescription = null) },
                label = { Text(text = stringResource(id = item.screen.labelResId)) },
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
    @DrawableRes val iconResId: Int,
    @DrawableRes val selectedIconResId: Int,
)

private val HomeNavigationItems = listOf(
    HomeNavigationItem(
        screen = Screen.Onboarding,
        iconResId = R.drawable.ic_mojito_outlined,
        selectedIconResId = R.drawable.ic_mojito_filled,
    ),
    HomeNavigationItem(
        screen = Screen.Collection,
        iconResId = R.drawable.ic_favorite_outlined,
        selectedIconResId = R.drawable.ic_favorite_filled,
    ),
    HomeNavigationItem(
        screen = Screen.Watched,
        iconResId = R.drawable.ic_person_outline,
        selectedIconResId = R.drawable.ic_person_filled,
    )
)
