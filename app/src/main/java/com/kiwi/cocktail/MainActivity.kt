package com.kiwi.cocktail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.analytics.FirebaseAnalytics
import com.kiwi.cocktail.ui.Home
import com.kiwi.common_ui_compose.theme.KiwisBarTheme
import com.kiwi.data.entities.DeviceTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var firebaseAnalytics: FirebaseAnalytics

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Turn off the decor fitting system windows, which means we need to through handling
        // insets
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()

            val useDarkColors =
                when (viewModel.deviceTheme.collectAsState(initial = DeviceTheme.SYSTEM).value) {
                    DeviceTheme.SYSTEM -> isSystemInDarkTheme()
                    DeviceTheme.LIGHT -> false
                    DeviceTheme.DARK -> true
                }
            val enableDynamicColors =
                viewModel.enableDynamicColors.collectAsState(initial = false).value

            SideEffect {
                systemUiController.setStatusBarColor(
                    color = Color.Transparent,
                    darkIcons = !useDarkColors,
                )
            }
            CompositionLocalProvider {
                KiwisBarTheme(
                    useDarkColors = useDarkColors,
                    enableDynamicColor = enableDynamicColors,
                ) {
                    Home(
                        firebaseAnalytics = firebaseAnalytics
                    )
                }
            }
        }
    }
}
