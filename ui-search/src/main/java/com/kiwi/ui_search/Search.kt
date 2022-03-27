package com.kiwi.ui_search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.kiwi.common_ui_compose.SampleFullDrinkEntityProvider
import com.kiwi.common_ui_compose.rememberStateWithLifecycle
import com.kiwi.data.entities.FullDrinkEntity

@Composable
fun Search(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
) {
    val uiState by rememberStateWithLifecycle(viewModel.uiState)

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
    var searchQuery by remember {
        mutableStateOf(
            TextFieldValue(
                text = uiState.query,
                selection = TextRange(uiState.query.length)
            )
        )
    }

    BackHandler(enabled = uiState.searchResult.isNotEmpty()) {
        searchQuery = TextFieldValue()
        onSearchQuery(searchQuery.text)
    }

    Scaffold(
        topBar = {
            SearchableTopBar(
                navigateUp = navigateUp,
                queryValue = searchQuery,
                onValueChange = { searchQuery = it },
                onSearch = { onSearchQuery(searchQuery.text) },
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        if (uiState.isSearching) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
        if (uiState.searchResult.isEmpty()) {
            Column(modifier = Modifier.fillMaxWidth()) {

                if (uiState.query.isNotBlank()) {
                    val message =
                        uiState.errorMessage ?: stringResource(id = R.string.no_result_caption)
                    NoResult(
                        message = message
                    )
                }
                RecommendResult(
                    uiState = uiState,
                    onSearchQuery = {
                        searchQuery = TextFieldValue(
                            text = it,
                            selection = TextRange(it.length),
                        )
                        onSearchQuery(it)
                    },
                    openRecipe = openRecipe,
                )
            }
        } else {
            SearchResult(
                data = uiState.searchResult,
                onItemClick = openRecipe
            )
        }
    }
}

@Composable
private fun SearchableTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    queryValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onSearch: () -> Unit,
) {
    SmallTopAppBar(
        modifier = modifier,
        title = {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = queryValue,
                onValueChange = onValueChange,
                onSearch = onSearch,
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

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            if (value.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onValueChange(TextFieldValue())
                        onSearch()
                    }
                ) {
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
            onSearch = {
                onSearch()
                focusManager.clearFocus()
            }
        ),
        placeholder = { Text(text = hint) },
        singleLine = true,
    )

    SideEffect {
        if (value.text.isEmpty()) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
private fun NoResult(
    message: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.no_result),
            style = MaterialTheme.typography.titleLarge
        )
        Text(text = message)
    }
}

@Composable
private fun SearchResult(
    data: List<CocktailUiState>,
    onItemClick: (String) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(data) {
            ResultCard(
                cocktail = it,
                onCardClick = { onItemClick(it.id) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun ResultCard(
    cocktail: CocktailUiState,
    onCardClick: () -> Unit,
) {
    Card(
        onClick = onCardClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(
                    data = cocktail.thumb,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                cocktail.ingredients?.let { ingredients ->
                    Text(
                        text = ingredients.joinToString { it },
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                cocktail.instructions?.let { instructions ->
                    Text(
                        text = instructions,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                Chip(
                    onClick = { },
                    colors = ChipDefaults.chipColors(
                        backgroundColor = MaterialTheme.colorScheme.onSurfaceVariant
                            .copy(alpha = 0.12f)
                            .compositeOver(MaterialTheme.colorScheme.surfaceVariant)
                    ),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = cocktail.category,
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendResult(
    uiState: SearchUiState,
    onSearchQuery: (String) -> Unit,
    openRecipe: (cocktailId: String) -> Unit
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
                text = stringResource(id = R.string.categories),
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
                text = stringResource(id = R.string.drink_again),
            )
            CocktailRow(
                data = uiState.randomCocktails,
                onItemClick = openRecipe
            )
        }
    }
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
    data: List<CocktailUiState>,
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
    cocktail: CocktailUiState,
    onClick: (cocktailId: String) -> Unit,
) {
    Card(
        onClick = { onClick.invoke(cocktail.id) },
        modifier = modifier
            .width(150.dp)
            .wrapContentHeight(),
    ) {
        Column {
            Image(
                painter = rememberImagePainter(
                    data = cocktail.thumb,
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
        queryValue = TextFieldValue("text"),
        onValueChange = {},
        onSearch = {},
    )
}

@Preview
@Composable
private fun PreviewNoResult() {
    NoResult("Message")
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

@Preview
@Composable
private fun PreviewResultCard(
    @PreviewParameter(SampleFullDrinkEntityProvider::class) cocktail: FullDrinkEntity
) {
    ResultCard(
        cocktail = CocktailUiState(
            id = cocktail.id,
            name = cocktail.name,
            thumb = cocktail.thumb,
            ingredients = cocktail.ingredients,
            instructions = cocktail.instructions,
            category = cocktail.category,
        ),
        onCardClick = {},
    )
}
