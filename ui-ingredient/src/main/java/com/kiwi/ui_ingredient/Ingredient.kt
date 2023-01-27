package com.kiwi.ui_ingredient

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.kiwi.common_ui_compose.ErrorLayout
import com.kiwi.common_ui_compose.ProgressLayout
import com.kiwi.common_ui_compose.rememberStateWithLifecycle

@Composable
fun Ingredient(
    viewModel: IngredientViewModel = hiltViewModel(),
    openRecipe: (String) -> Unit,
) {
    val uiState by rememberStateWithLifecycle(viewModel.uiState)
    Ingredient(
        uiState = uiState,
        openRecipe = openRecipe,
    )
}

@Composable
private fun Ingredient(
    uiState: IngredientUiState,
    openRecipe: (String) -> Unit,
) {
    if (uiState.isLoading) {
        ProgressLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
        )
    }

    uiState.errorMessage?.let {
        ErrorLayout(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            errorMessage = it
        )
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            DraggableIndicator()
        }

        if (!uiState.isLoading && uiState.errorMessage == null) {
            item {
                IngredientTitle(uiState.name)
            }
            item {
                IngredientImage(
                    imageUrl = stringResource(
                        id = R.string.ingredient_medium_image_url,
                        uiState.name
                    )
                )
            }
            item {
                IngredientDesc(
                    desc = uiState.desc.ifEmpty {
                        stringResource(id = R.string.default_ingredient_desc, uiState.name)
                    }
                )
            }
            item {
                Text(
                    text = stringResource(id = R.string.also_use_cocktails, uiState.name),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                )
            }
            item {
                AlsoUseList(
                    cocktails = uiState.alsoUseCocktails,
                    onItemClick = openRecipe,
                )
            }
        }
    }
}

@Composable
private fun DraggableIndicator() {
    Box(
        modifier = Modifier
            .size(width = 48.dp, height = 6.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    )
}

@Composable
private fun IngredientTitle(ingredientName: String) {
    Text(
        text = ingredientName,
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
private fun IngredientImage(imageUrl: String) {
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = { crossfade(true) }
        ),
        contentDescription = null,
        modifier = Modifier.size(128.dp)
    )
}

@Composable
private fun IngredientDesc(desc: String) {
    SelectionContainer {
        Text(
            text = desc,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )
    }
}

@Composable
private fun AlsoUseList(
    cocktails: List<CocktailUiState>,
    onItemClick: (String) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(cocktails) {
            CommonIngredientCocktailCard(
                modifier = Modifier
                    .width(128.dp)
                    .fillMaxHeight(),
                cocktail = it,
                onCardClick = { onItemClick(it.id) },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonIngredientCocktailCard(
    modifier: Modifier = Modifier,
    cocktail: CocktailUiState,
    onCardClick: () -> Unit,
) {
    Card(
        onClick = { onCardClick() },
        modifier = modifier,
    ) {
        Column {
            Image(
                painter = rememberImagePainter(
                    data = cocktail.imageUrl,
                    builder = {
                        crossfade(true)
                    },
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
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
