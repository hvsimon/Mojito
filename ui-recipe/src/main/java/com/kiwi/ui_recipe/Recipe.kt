package com.kiwi.ui_recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.Ingredient
import java.util.UUID

@Composable
fun Recipe(
    viewModel: RecipeViewModel = hiltViewModel(),
) {
    val uiState by rememberFlowWithLifecycle(viewModel.uiState)
        .collectAsState(initial = RecipeUiState())

    Recipe(
        cocktail = uiState.cocktail,
        isFollowed = uiState.isFollowed,
        onToggleFollowed = { viewModel.toggleFollow() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Recipe(
    cocktail: CocktailPo?,
    isFollowed: Boolean,
    onToggleFollowed: () -> Unit,
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
                Ingredient(cocktail.ingredients)
                Step(cocktail.steps)
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
private fun Ingredient(ingredients: List<Ingredient>) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.ingredients),
            style = MaterialTheme.typography.headlineLarge,
        )
        ingredients.forEach {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineSmall,
                )
                Text(
                    text = it.amount,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun Step(
    steps: List<String>,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = stringResource(id = R.string.step),
            style = MaterialTheme.typography.headlineLarge,
        )

        Text(
            text = steps.joinToString(separator = "\n"),
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
fun PreviewRecipe() {
    Recipe(
        cocktail = CocktailPo(
            cocktailId = UUID.randomUUID().toString(),
            name = "Mojito2",
            gallery = listOf(),
            ingredients = listOf(
                Ingredient(
                    name = "\uD83C\uDF78 白蘭姆酒",
                    amount = "2 shots"
                ),
                Ingredient(
                    name = "\uD83E\uDDC2 糖漿",
                    amount = "0.5 shot"
                ),
                Ingredient(
                    name = "\uD83C\uDF4B 萊姆片",
                    amount = "4"
                ),
                Ingredient(
                    name = "\uD83C\uDF3F 新鮮薄荷",
                    amount = "12 leaves"
                ),
                Ingredient(
                    name = "\uD83E\uDD64 蘇打水",
                    amount = "fill to top"
                ),
            ),
            steps = listOf("1. ", "2.")
        ),
        isFollowed = false,
        onToggleFollowed = {}
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
