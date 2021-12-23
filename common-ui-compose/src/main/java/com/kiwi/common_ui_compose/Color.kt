package com.kiwi.common_ui_compose

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val Blue40 = Color(0xff1e40ff)
private val DarkBlue40 = Color(0xff3e41f4)
private val Yellow40 = Color(0xff7d5700)
// Remaining colors from tonal palettes

val LightColorScheme = lightColorScheme(
    primary = Blue40,
    secondary = DarkBlue40,
    tertiary = Yellow40,
    // error, primaryContainer, onSecondary, etc.
)
val DarkColorScheme = darkColorScheme(
    primary = Blue40,
    secondary = DarkBlue40,
    tertiary = Yellow40,
    // error, primaryContainer, onSecondary, etc.
)

@Composable
fun kiwiColorScheme(
    useDarkColors: Boolean = isSystemInDarkTheme(),
): ColorScheme {
    val context = LocalContext.current
    return when {
        // Material You colors for Android 12+
        Build.VERSION.SDK_INT >= 31 -> {
            when {
                useDarkColors -> dynamicDarkColorScheme(context)
                else -> dynamicLightColorScheme(context)
            }
        }
        useDarkColors -> DarkColorScheme
        else -> LightColorScheme
    }
}
