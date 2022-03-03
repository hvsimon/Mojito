package com.kiwi.ui_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle

@Composable
fun Search(
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val uiState by rememberFlowWithLifecycle(viewModel.uiState)
        .collectAsState(initial = SearchUiState())

    Search(
        categories = uiState.categories,
        ingredients = uiState.ingredients,
    )
}

@Composable
private fun Search(
    categories: List<String>,
    ingredients: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        TagGroup(data = categories)
    }
}

@Composable
private fun TagGroup(
    modifier: Modifier = Modifier,
    data: List<String>,
) {
    FlowRow(
        modifier = modifier.padding(16.dp),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp,
    ) {
        data.forEach {
            // TODO: change to material3.chip when it released
            Text(text = it)
        }
    }
}

@Preview
@Composable
private fun PreviewCategoryGroup() {
    val repeatRange = 1..10
    val list = listOf('a'..'z')
        .flatten()
        .map { it.toString().repeat(repeatRange.random()) }

    TagGroup(data = list)
}
