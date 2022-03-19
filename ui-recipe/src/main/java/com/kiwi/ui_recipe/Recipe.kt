package com.kiwi.ui_recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.kiwi.common_ui_compose.ErrorLayout
import com.kiwi.common_ui_compose.ProgressLayout
import com.kiwi.common_ui_compose.SampleFullDrinkEntityProvider
import com.kiwi.common_ui_compose.rememberStateWithLifecycle
import com.kiwi.data.entities.FullDrinkEntity

@Composable
fun Recipe(
    viewModel: RecipeViewModel = hiltViewModel(),
    openIngredient: (ingredientName: String) -> Unit,
) {
    val uiState by rememberStateWithLifecycle(viewModel.uiState)

    val isLoading = uiState.isLoading
    if (isLoading) {
        ProgressLayout(modifier = Modifier.fillMaxSize())
        return
    }

    val errorMessage = uiState.errorMessage
    if (errorMessage != null) {
        ErrorLayout(
            modifier = Modifier.fillMaxSize(),
            errorMessage = errorMessage,
        )
        return
    }

    uiState.cocktail?.let { cocktail ->
        Recipe(
            cocktail = cocktail,
            isFollowed = uiState.isFollowed,
            onToggleFollowed = { viewModel.toggleFollow() },
            openIngredient = openIngredient,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Recipe(
    cocktail: FullDrinkEntity,
    isFollowed: Boolean,
    onToggleFollowed: () -> Unit,
    openIngredient: (ingredientName: String) -> Unit,
) {
    val scrollState = rememberScrollState()
    Scaffold(
        floatingActionButton = {
            ToggleFollowFloatingActionButton(
                isFollowed = isFollowed,
                onClick = onToggleFollowed
            )
        }
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = cocktail.thumb,
                    builder = {
                        size(OriginalSize)
                    },
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            )
            Column {
                Introduction(
                    cocktailName = cocktail.name,
                )
                Ingredients(
                    ingredients = cocktail.ingredients,
                    measures = cocktail.measures,
                    onItemClick = openIngredient,
                )
                Step(cocktail.instructions)
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                )
            }
        }
    }
}

@Composable
private fun Introduction(
    cocktailName: String,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = cocktailName,
            style = MaterialTheme.typography.displaySmall,
        )
    }
}

@Composable
private fun Ingredients(
    ingredients: List<String>,
    measures: List<String>,
    onItemClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.ingredients),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
        ingredients.zip(measures) { ingredient, measure ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .clickable { onItemClick(ingredient) }
                    .padding(horizontal = 16.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = stringResource(id = R.string.ingredient_small_image_url, ingredient)
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = ingredient,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = measure,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun Step(
    steps: String,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.step),
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            text = steps,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun ToggleFollowFloatingActionButton(
    isFollowed: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = when {
                isFollowed -> Icons.Default.Favorite
                else -> Icons.Default.FavoriteBorder
            },
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun PreviewRecipe(
    @PreviewParameter(SampleFullDrinkEntityProvider::class) cocktail: FullDrinkEntity
) {
    Recipe(
        cocktail = cocktail,
        isFollowed = false,
        onToggleFollowed = {},
        openIngredient = {},
    )
}

@Preview
@Composable
private fun PreviewFollowFAB() {
    ToggleFollowFloatingActionButton(
        isFollowed = false,
        onClick = {},
    )
}
