package com.kiwi.common_ui_compose

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProgressLayout(
    modifier: Modifier = Modifier,
) {
    // TODO: redesign
    Box(
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
        )
    }
}

@Preview
@Composable
private fun PreviewProgressLayout() {
    ProgressLayout()
}
