package com.kiwi.ui_onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.CocktailPo

@Composable
fun Onboarding(
    viewModel: OnboardingViewModel = hiltViewModel(),
    openSearch: () -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
    openCocktailList: (String) -> Unit,
) {
    val uiState by rememberFlowWithLifecycle(viewModel.uiState)
        .collectAsState(initial = OnboardingUiState())

    Onboarding(
        uiState = uiState,
        openSearch = openSearch,
        openRecipe = { viewModel.randomCocktail() },
        openCocktailList = openCocktailList,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun Onboarding(
    uiState: OnboardingUiState,
    openSearch: () -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
    openCocktailList: (String) -> Unit,
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
    ) {
        item(span = { GridItemSpan(2) }) {
            Header(
                cocktail = uiState.coverCocktail,
                onSearchClick = openSearch,
                onRandomClick = openRecipe,
                modifier = Modifier.aspectRatio(0.75f)
            )
        }
        item(span = { GridItemSpan(2) }) {
            Text(
                text = stringResource(id = R.string.six_base_wine),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(uiState.baseWineGroups) {
            WineCard(
                imageData = it.groupImageUrl,
                label = it.groupName,
                onCardClick = openCocktailList,
            )
        }

        item(span = { GridItemSpan(2) }) {
            Text(
                text = stringResource(id = R.string.iba_official_cocktail_list),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(
            items = uiState.ibaCategories,
            span = { GridItemSpan(2) },
        ) {
            CategoryCard(
                imageData = it.imageUrl,
                categoryName = it.name,
                onCardClick = { /* TODO */ },
            )
        }

        item {
            WineCard(
                imageData = R.drawable.ic_glass_poring,
                label = "戴眼鏡的波利",
                onCardClick = {},
            )
        }

        item {
            WineCard(
                imageData = R.drawable.ic_tenerife_dog,
                label = "狗勾",
                onCardClick = {},
            )
        }
    }
}

// TODO: redesign header due to the drink's image is always square (700x700)
@Composable
private fun Header(
    cocktail: CocktailPo?,
    onSearchClick: () -> Unit,
    onRandomClick: (cocktailId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = rememberImagePainter(
                data = cocktail?.gallery?.random(),
                builder = {
                    size(OriginalSize)
                    crossfade(true)
                },
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
        SearchBar(
            onSearchClick = onSearchClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .statusBarsPadding()
        )
        cocktail?.let { cocktail ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                        )
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = cocktail.name,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                )
                Button(
                    onClick = { onRandomClick(cocktail.cocktailId) },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(id = R.string.flexible))
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onSearchClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
        Text(text = stringResource(id = R.string.drink_something))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WineCard(
    imageData: Any,
    label: String,
    onCardClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f)
            .clickable { onCardClick.invoke(label) },
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
    onCardClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(2f)
            .clickable { onCardClick.invoke(categoryName) },
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

@Preview
@Composable
private fun PreviewSearchBar() {
    SearchBar(onSearchClick = {})
}

@Preview
@Composable
private fun PreviewHeader() {
    Header(cocktail = null, onSearchClick = {}, onRandomClick = {})
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
