package com.kiwi.ui_about

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun About() {
    Box(
        modifier = Modifier.statusBarsPadding()
    ) {
        Text(text = "About")
    }
}
