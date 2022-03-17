package com.kiwi.common_ui_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorLayout(
    errorMessage: String,
) {
    // TODO: redesign
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_tenerife_dog),
            contentDescription = null,
            modifier = Modifier.size(88.dp)
        )
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun PreviewErrorLayout() {
    ErrorLayout(
        errorMessage = "Error message",
    )
}
