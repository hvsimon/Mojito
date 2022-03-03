package com.kiwi.ui_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Search() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Search")
    }
}

@Preview
@Composable
fun PreviewSearch() {
    Search()
}
