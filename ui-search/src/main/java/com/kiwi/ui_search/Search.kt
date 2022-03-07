package com.kiwi.ui_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle

@Composable
fun Search(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
) {
    val uiState by rememberFlowWithLifecycle(viewModel.uiState)
        .collectAsState(initial = SearchUiState())

    Search(
        uiState = uiState,
        navigateUp = navigateUp,
        onSearchQuery = { viewModel.search(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Search(
    uiState: SearchUiState,
    navigateUp: () -> Unit,
    onSearchQuery: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            SearchableTopBar(
                navigateUp = navigateUp,
                query = uiState.query,
                onSearch = onSearchQuery,
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
        ) {
            TagGroup(data = uiState.categories)
        }
    }
}

@Composable
private fun SearchableTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    query: String,
    onSearch: (String) -> Unit,
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue(query)) }

    SmallTopAppBar(
        modifier = modifier,
        title = {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = searchQuery,
                onValueChange = { value ->
                    searchQuery = value
                },
                onSearch = { onSearch(searchQuery.text) },
                hint = stringResource(id = R.string.drink_something)
            )
        },
        navigationIcon = {
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onSearch: () -> Unit,
    hint: String,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            if (value.text.isNotEmpty()) {
                IconButton(onClick = { onValueChange(TextFieldValue()) }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        ),
        placeholder = { Text(text = hint) },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
        ),
    )
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
private fun PreviewTopBar() {
    SearchableTopBar(
        navigateUp = {},
        query = "Query",
        onSearch = {},
    )
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
