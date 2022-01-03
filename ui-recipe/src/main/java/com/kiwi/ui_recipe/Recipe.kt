package com.kiwi.ui_recipe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.RecipeEntity
import com.kiwi.data.entities.Ingredient

@Composable
fun Recipe(
    viewModel: RecipeViewModel = hiltViewModel(),
) {
    val recipe: RecipeEntity? by rememberFlowWithLifecycle(viewModel.cocktail).collectAsState(initial = null)
    if (recipe == null) {
        // show loading view
        return
    }

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = rememberImagePainter(
                data = recipe!!.cocktail.gallery.firstOrNull(),
                builder = {
                    size(OriginalSize)
                },
            ),
            contentScale = ContentScale.Inside,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Column {
            Introduction(
                cocktailName = recipe!!.cocktail.name,
                cocktailIntro = recipe!!.cocktail.intro
            )
            Ingredient(recipe!!.ingredients)
            Step(recipe!!.cocktail.steps)
            Tips(recipe!!.cocktail.tipsAndTricks)
        }
    }
}

@Composable
private fun Introduction(
    cocktailName: String,
    cocktailIntro: String,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = cocktailName,
            style = MaterialTheme.typography.displaySmall,
        )

        Text(
            text = cocktailIntro,
            style = MaterialTheme.typography.bodyLarge,
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
private fun Tips(
    tips: Set<String>,
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.tips),
            style = MaterialTheme.typography.headlineLarge,
        )

        Text(
            text = tips.joinToString(separator = "\n"),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}
