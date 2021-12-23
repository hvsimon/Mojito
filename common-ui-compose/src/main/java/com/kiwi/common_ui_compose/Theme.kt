package com.kiwi.cocktail

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kiwi.common_ui_compose.kiwiColorScheme

@Composable
fun KiwisBarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = kiwiColorScheme(darkTheme),
        content = content,
    )
}