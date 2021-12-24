package com.kiwi.cocktail.ui

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
            
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            // <div>Icons made by <a href="https://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
            val iconRes = if(selected) R.drawable.ic_mojito_black else R.drawable.ic_mojito_line
            NavigationBarItem(
                icon = { Icon(painterResource(id = iconRes), contentDescription = null) },
                label = { Text(text = stringResource(id = item.labelResId)) },
                selected = selected,
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
    Screen.Collection,
    Screen.Watched,
)
