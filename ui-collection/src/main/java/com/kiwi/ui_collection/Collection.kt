package com.kiwi.ui_collection

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun Collection() {
    Column(Modifier.statusBarsPadding()) {
        Text(text = "Collection")
    }
}
