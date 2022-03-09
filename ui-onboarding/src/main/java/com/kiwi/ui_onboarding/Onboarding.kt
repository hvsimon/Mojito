package com.kiwi.ui_onboarding

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.Ingredient

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
        openRecipe = openRecipe,
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

@Composable
private fun Header(
    cocktail: CocktailPo?,
    onSearchClick: () -> Unit,
    onRandomClick: (cocktailId: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(Color.Black)
            .statusBarsPadding()
    ) {
        SearchBar(
            onSearchClick = onSearchClick,
            modifier = Modifier
                .padding(8.dp)
        )
        RandomCocktail(cocktail = cocktail)
        IngredientCardRow(cocktail?.ingredients ?: emptyList())
        RandomButton(
            onClick = { cocktail?.let { onRandomClick(it.cocktailId) } }
        )
    }
}

@Composable
private fun RandomCocktail(
    cocktail: CocktailPo? = null,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Image(
            painter = rememberImagePainter(
                data = cocktail?.gallery?.random(),
                builder = {
                    crossfade(true)
                },
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
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
    ingredients: List<Ingredient>,
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
                imageUrl = stringResource(id = R.string.ingredient_image_url, it.name),
                name = it.name,
            )
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
private fun IngredientCard(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .clickable {
                Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
            }
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
            Text(text = stringResource(id = R.string.flexible))
        }
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
private fun PreviewIngredientCardRow() {
    IngredientCardRow(
        listOf(
            Ingredient(name = "a", amount = "a"),
        )
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
