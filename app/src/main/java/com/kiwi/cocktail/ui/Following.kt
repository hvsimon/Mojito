package com.kiwi.cocktail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.statusBarsPadding

@Preview
@Composable
fun Following() {
    Column(Modifier.statusBarsPadding()) {
        Text(text = "Following")
    }
}
