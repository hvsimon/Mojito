package com.kiwi.ui_recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import com.kiwi.common_ui_compose.SampleCocktailPoProvider
import com.kiwi.common_ui_compose.rememberStateWithLifecycle
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.Ingredient

@Composable
fun Recipe(
    viewModel: RecipeViewModel = hiltViewModel(),
    openIngredient: (ingredientName: String) -> Unit,
) {
    val uiState by rememberStateWithLifecycle(viewModel.uiState)

    Recipe(
        cocktail = uiState.cocktail,
        isFollowed = uiState.isFollowed,
        onToggleFollowed = { viewModel.toggleFollow() },
        openIngredient = openIngredient,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Recipe(
    cocktail: CocktailPo?,
    isFollowed: Boolean,
    onToggleFollowed: () -> Unit,
    openIngredient: (ingredientName: String) -> Unit,
) {
    if (cocktail == null) {
        // show loading view
        return
    }

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
                    data = cocktail.gallery.firstOrNull(),
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
                    onItemClick = openIngredient,
                )
                Step(cocktail.steps)
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
    ingredients: List<Ingredient>,
    onItemClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.ingredients),
            style = MaterialTheme.typography.headlineLarge,
        )
        ingredients.forEach {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .clickable { onItemClick(it.name) }
                    .padding(4.dp)
            ) {
                Image(
                    painter = rememberImagePainter(
                        data = stringResource(id = R.string.ingredient_small_image_url, it.name)
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = it.amount,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
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
    @PreviewParameter(SampleCocktailPoProvider::class) cocktailPo: CocktailPo
) {
    Recipe(
        cocktail = cocktailPo,
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
