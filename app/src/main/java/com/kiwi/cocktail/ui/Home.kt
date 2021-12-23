package com.kiwi.cocktail.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.navigationBarsPadding
import com.kiwi.cocktail.AppNavigation
import com.kiwi.cocktail.Screen

@Preview
@Composable
fun Home() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        bottomBar = { HomeBottomNavigation(navController) },
    ) { innerPadding ->
        AppNavigation(navController = navController, modifier = Modifier.padding(innerPadding))
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
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = null) },
                label = { Text(text = stringResource(id = item.labelResId)) },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

private val HomeNavigationItems = listOf(
    Screen.Onboarding,
    Screen.Following,
    Screen.Watched,
)
