package com.kiwi.ui_search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
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
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.Precision
import coil.transform.RoundedCornersTransformation
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.kiwi.common_ui_compose.ProgressLayout
import com.kiwi.common_ui_compose.SampleFullDrinkEntityProvider
import com.kiwi.common_ui_compose.rememberStateWithLifecycle
import com.kiwi.data.entities.FullDrinkEntity
import com.kiwi.data.entities.SimpleDrinkDto
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    openCocktailListWithCategory: (String) -> Unit,
    openCocktailListWithFirstLetter: (String) -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val uiState by rememberStateWithLifecycle(viewModel.uiState)

    Search(
        uiState = uiState,
        navigateUp = navigateUp,
        openCocktailListWithCategory = {
            openCocktailListWithCategory(it)
            keyboardController?.hide()
        },
        openCocktailListWithFirstLetter = {
            openCocktailListWithFirstLetter(it)
            keyboardController?.hide()
        },
        onSearchQuery = { viewModel.search(it) },
        openRecipe = {
            openRecipe(it)
            keyboardController?.hide()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun Search(
    uiState: SearchUiState,
    navigateUp: () -> Unit,
    openCocktailListWithCategory: (String) -> Unit,
    openCocktailListWithFirstLetter: (String) -> Unit,
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
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BackHandler(
        enabled = uiState.query.isNotEmpty() || drawerState.isOpen
    ) {
        if (drawerState.isOpen) {
            scope.launch { drawerState.close() }
        } else {
            searchQuery = TextFieldValue()
            onSearchQuery(searchQuery.text)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    DrawerContent(
                        listByFirstLetter = openCocktailListWithFirstLetter,
                    )
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Scaffold(
                        topBar = {
                            SearchableTopBar(
                                navigateUp = navigateUp,
                                queryValue = searchQuery,
                                onValueChange = { searchQuery = it },
                                onSearch = { onSearchQuery(searchQuery.text) },
                                openDrawer = {
                                    scope.launch { drawerState.open() }
                                    keyboardController?.hide()
                                },
                                enableAutoFocus = drawerState.isClosed,
                            )
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding()
                    ) {
                        if (uiState.query.isEmpty()) {
                            RecommendResult(
                                uiState = uiState,
                                openCocktailListWithCategory = openCocktailListWithCategory,
                                openRecipe = openRecipe,
                            )
                        } else {
                            SearchResult(
                                searchByNameResult = uiState.searchByNameResult,
                                searchByIngredientResult = uiState.searchByIngredientResult,
                                onItemClick = openRecipe
                            )
                        }
                    }
                }
            }
        )
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
    openDrawer: () -> Unit,
    enableAutoFocus: Boolean = true,
) {
    SmallTopAppBar(
        modifier = modifier,
        title = {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                value = queryValue,
                onValueChange = onValueChange,
                onSearch = onSearch,
                hint = stringResource(id = R.string.drink_something),
                enableAutoFocus = enableAutoFocus,
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
        actions = {
            IconButton(onClick = openDrawer) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_sort_by_alpha_24),
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
    enableAutoFocus: Boolean = true,
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
        if (enableAutoFocus && value.text.isEmpty()) {
            focusRequester.requestFocus()
        }
    }
}

@Composable
private fun DrawerContent(
    listByFirstLetter: (String) -> Unit,
) {

    val alphabet = remember { ('A'..'Z').toList().map { it.toString() } }
    val number = remember { ('0'..'9').toList().map { it.toString() } }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        Text(
            text = "List all cocktails by first letter",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp),
        )
        DrawerFilter(
            modifier = Modifier.padding(horizontal = 16.dp),
            data = alphabet,
            onButtonClick = listByFirstLetter,
        )
        DrawerFilter(
            modifier = Modifier.padding(horizontal = 16.dp),
            data = number,
            onButtonClick = listByFirstLetter,
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DrawerFilter(
    modifier: Modifier = Modifier,
    data: List<String>,
    onButtonClick: (String) -> Unit,
) {
    FlowRow(
        modifier = modifier.wrapContentHeight(),
        mainAxisSpacing = 8.dp,
    ) {
        data.forEach {
            FilledTonalButton(
                onClick = { onButtonClick(it) },
            ) {
                Text(text = it)
            }
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
    ) {
        Text(
            text = stringResource(id = R.string.no_result),
            style = MaterialTheme.typography.titleLarge
        )
        Text(text = message)
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun SearchResult(
    searchByNameResult: SearchResultUiState<CocktailUiState>,
    searchByIngredientResult: SearchResultUiState<SimpleDrinkDto>,
    onItemClick: (String) -> Unit,
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val pages = listOf(
        stringResource(
            R.string.search_by_name_with_total,
            searchByNameResult.data.size
        ),
        stringResource(
            R.string.search_by_ingredient_with_total,
            searchByIngredientResult.data.size
        )
    )

    Column {
        TabRow(
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            }
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(text = title) },
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                )
            }
        }

        HorizontalPager(
            count = pages.size,
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            when (page) {
                0 -> SearchByNameResult(searchByNameResult, onItemClick)
                1 -> SearchByIngredientResult(searchByIngredientResult, onItemClick)
            }
        }
    }
}

@Composable
private fun SearchByNameResult(
    result: SearchResultUiState<CocktailUiState>,
    onItemClick: (String) -> Unit
) {
    if (result.isSearching) {
        ProgressLayout(modifier = Modifier.fillMaxSize())
    }

    val data = result.data
    if (data.isEmpty() && !result.isSearching) {
        NoResult(message = stringResource(id = R.string.no_result_caption))
        return
    }

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

@Composable
private fun SearchByIngredientResult(
    result: SearchResultUiState<SimpleDrinkDto>,
    onItemClick: (String) -> Unit
) {
    if (result.isSearching) {
        ProgressLayout(modifier = Modifier.fillMaxSize())
    }

    val data = result.data
    if (data.isEmpty() && !result.isSearching) {
        NoResult(message = stringResource(id = R.string.no_result_caption))
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(data) {
            SearchByIngredientItem(
                cocktail = it,
                onClick = { onItemClick(it.id) },
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
private fun SearchByIngredientItem(
    cocktail: SimpleDrinkDto,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = cocktail.thumb,
                builder = {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(radius = 16.dp.value))
                    precision(Precision.EXACT)
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        Text(text = cocktail.name)
    }
}

@Composable
private fun RecommendResult(
    uiState: SearchUiState,
    openCocktailListWithCategory: (String) -> Unit,
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
                onChipClick = openCocktailListWithCategory,
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
        openDrawer = {},
        enableAutoFocus = true,
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
