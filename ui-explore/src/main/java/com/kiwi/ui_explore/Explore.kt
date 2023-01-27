package com.kiwi.ui_explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.kiwi.common_ui_compose.rememberStateWithLifecycle
import com.kiwi.data.entities.BaseLiquorType
import com.kiwi.data.entities.FullDrinkEntity
import com.kiwi.data.entities.IBACategoryType

@Composable
fun Explore(
    viewModel: ExploreViewModel = hiltViewModel(),
    openSearch: () -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
    openCocktailListWithBaseLiquor: (baseLiquorType: BaseLiquorType) -> Unit,
    openCocktailListWithIBACategory: (ibaCategoryType: IBACategoryType) -> Unit,
    openIngredient: (ingredientName: String) -> Unit,
) {
    val uiState by rememberStateWithLifecycle(viewModel.uiState)

    Explore(
        uiState = uiState,
        openSearch = openSearch,
        openRecipe = openRecipe,
        openCocktailListWithBaseLiquor = openCocktailListWithBaseLiquor,
        openCocktailListWithIBACategory = openCocktailListWithIBACategory,
        onRefresh = { viewModel.randomCocktail() },
        openIngredient = openIngredient,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun Explore(
    uiState: ExploreUiState,
    openSearch: () -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
    openCocktailListWithBaseLiquor: (baseLiquorType: BaseLiquorType) -> Unit,
    openCocktailListWithIBACategory: (ibaCategoryType: IBACategoryType) -> Unit,
    onRefresh: () -> Unit,
    openIngredient: (ingredientName: String) -> Unit,
) {
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            SearchBar(
                onSearchClick = openSearch,
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Black,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface.compositeOver(
                        MaterialTheme.colorScheme.primary
                    ),
                )
            )
        }
    ) { paddingValues ->

        val pullRefreshState = rememberPullRefreshState(uiState.isRefreshing, onRefresh)

        Box(
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .padding(top = paddingValues.calculateTopPadding()),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Header(
                    cocktail = uiState.coverCocktail,
                    onRandomClick = openRecipe,
                    onIngredientClick = openIngredient,
                )
                Text(
                    text = stringResource(id = R.string.six_base_liquors),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(16.dp)
                )
                uiState.baseLiquorItemUiStates.chunked(2).forEach {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        it.forEach {
                            WineCard(
                                imageData = it.imageUrl,
                                label = stringResource(id = it.displayTextResId),
                                onCardClick = { openCocktailListWithBaseLiquor(it.type) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
                Text(
                    text = stringResource(id = R.string.iba_official_cocktail_list),
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.padding(16.dp)
                )
                uiState.ibaCategoryUiStates.forEach {
                    CategoryCard(
                        imageData = it.imageUrl,
                        categoryName = stringResource(id = it.displayTextResId),
                        onCardClick = { openCocktailListWithIBACategory(it.type) },
                    )
                }
            }
            PullRefreshIndicator(
                refreshing = uiState.isRefreshing,
                state = pullRefreshState,
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = contentColorFor(MaterialTheme.colorScheme.surface),
                modifier = Modifier.align(Alignment.TopCenter),
            )
        }
    }
}

@Composable
private fun Header(
    cocktail: FullDrinkEntity?,
    onRandomClick: (cocktailId: String) -> Unit,
    onIngredientClick: (ingredientName: String) -> Unit,
) {

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxWidth()
    ) {
        RandomCocktail(cocktail = cocktail)
        IngredientCardRow(
            ingredients = cocktail?.ingredients ?: emptyList(),
            onItemClick = onIngredientClick
        )
        RandomButton(
            onClick = { cocktail?.let { onRandomClick(it.id) } }
        )
    }
}

@Composable
private fun RandomCocktail(
    cocktail: FullDrinkEntity? = null,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .fillMaxWidth()
            .aspectRatio(1f)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberImagePainter(
                data = cocktail?.thumb,
                builder = { crossfade(true) },
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        cocktail?.let { cocktail ->
            Text(
                text = cocktail.name,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                        )
                    )
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun IngredientCardRow(
    ingredients: List<String>,
    onItemClick: (ingredientName: String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ingredients.forEach {
            IngredientCard(
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f),
                imageUrl = stringResource(id = R.string.ingredient_small_image_url, it),
                onCardClick = { onItemClick(it) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit,
    colors: TopAppBarColors = TopAppBarDefaults.smallTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    val offsetLimit = with(LocalDensity.current) { -64.0.dp.toPx() }
    SideEffect {
        if (scrollBehavior?.state?.heightOffsetLimit != offsetLimit) {
            scrollBehavior?.state?.heightOffsetLimit = offsetLimit
        }
    }

    // val scrollFraction = scrollBehavior?.state?.overlappedFraction ?: 0f
    // FIXME: Cannot access 'containerColor': it is internal in 'TopAppBarColors'
    // val appBarContainerColor = colors.containerColor(scrollFraction)

    Surface(
        // color = appBarContainerColor,
        shadowElevation = 3.dp
    ) {
        ElevatedButton(
            onClick = onSearchClick,
            modifier = modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
            Text(text = stringResource(id = R.string.drink_something))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun IngredientCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onCardClick: () -> Unit,
) {
    Card(
        onClick = onCardClick,
        modifier = modifier,
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = { crossfade(true) },
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}

@Composable
fun RandomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Button(onClick = onClick) {
            Text(text = stringResource(id = R.string.lets_go))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WineCard(
    imageData: Any,
    label: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onCardClick,
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f),
    ) {
        Box {
            Image(
                painter = rememberImagePainter(data = imageData),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.5f))
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryCard(
    imageData: Any,
    categoryName: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onCardClick,
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(2f),
    ) {
        Box {
            Image(
                painter = rememberImagePainter(data = imageData),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.5f))
            ) {
                Text(
                    text = categoryName,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun PreviewSearchBar() {
    SearchBar(onSearchClick = {})
}

@Preview
@Composable
private fun PreviewIngredientCardRow() {
    IngredientCardRow(
        ingredients = listOf("a", "b"),
        onItemClick = {}
    )
}

@Preview
@Composable
private fun PreviewRandomButton() {
    RandomButton(
        onClick = {}
    )
}

@Preview
@Composable
private fun PreviewWineCard() {
    WineCard(imageData = "", label = "Rum", onCardClick = {})
}

@Preview
@Composable
private fun PreviewCategoryCard() {
    CategoryCard(imageData = "", categoryName = "The Unforgettables", onCardClick = {})
}
