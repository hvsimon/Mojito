package com.kiwi.ui_search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.CocktailPo

@Composable
fun Search(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
) {
    val uiState by rememberFlowWithLifecycle(viewModel.uiState)
        .collectAsState(initial = SearchUiState())

    Search(
        uiState = uiState,
        navigateUp = navigateUp,
        onSearchQuery = { viewModel.search(it) },
        openRecipe = openRecipe,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Search(
    uiState: SearchUiState,
    navigateUp: () -> Unit,
    onSearchQuery: (String) -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
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
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (uiState.categories.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp),
                    text = "Categories",
                )
                TagGroup(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    data = uiState.categories,
                    onChipClick = onSearchQuery,
                )
            }

            if (uiState.randomCocktails.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp),
                    text = "Drink again?",
                )
                CocktailRow(
                    data = uiState.randomCocktails,
                    onItemClick = openRecipe
                )
            }
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun TagGroup(
    modifier: Modifier = Modifier,
    data: List<String>,
    onChipClick: (String) -> Unit,
) {
    FlowRow(
        modifier = modifier.wrapContentHeight(),
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp,
    ) {
        data.forEach {
            // TODO: change to material3.chip when it released
            Chip(
                modifier = Modifier.height(32.dp),
                onClick = { onChipClick(it) },
                colors = ChipDefaults.chipColors(
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant
                ),
            ) {
                Text(text = it)
            }
        }
    }
}

@Composable
private fun CocktailRow(
    modifier: Modifier = Modifier,
    data: List<CocktailPo>,
    onItemClick: (String) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(data) {
            CocktailCard(
                cocktail = it,
                onClick = onItemClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CocktailCard(
    modifier: Modifier = Modifier,
    cocktail: CocktailPo,
    onClick: (cocktailId: String) -> Unit,
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .wrapContentHeight()
            .clickable(onClick = { onClick.invoke(cocktail.cocktailId) }),
    ) {
        Column {
            Image(
                painter = rememberImagePainter(
                    data = cocktail.gallery.randomOrNull(),
                    builder = {
                        crossfade(true)
                    },
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )

            Text(
                text = cocktail.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                modifier = Modifier.padding(16.dp)
            )
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
private fun PreviewTagGroup() {
    val repeatRange = 1..10
    val list = listOf('a'..'g')
        .flatten()
        .map { it.toString().repeat(repeatRange.random()) }

    TagGroup(
        data = list,
        onChipClick = {},
    )
}
