package com.kiwi.ui_ingredient

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.kiwi.common_ui_compose.rememberStateWithLifecycle

@Composable
fun Ingredient(
    viewModel: IngredientViewModel = hiltViewModel(),
) {
    val uiState by rememberStateWithLifecycle(viewModel.uiState)
    Ingredient(
        uiState = uiState,
    )
}

@Composable
private fun Ingredient(
    uiState: IngredientUiState,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.5f)
    ) {
        item {
            DraggableIndicator()
        }
        item {
            IngredientTitle(uiState.name)
        }
        item {
            IngredientImage(
                imageUrl = stringResource(id = R.string.ingredient_medium_image_url, uiState.name)
            )
        }
        if (!uiState.isLoading) {
            item {
                IngredientDesc(
                    desc = uiState.desc.ifEmpty {
                        stringResource(id = R.string.default_ingredient_desc, uiState.name)
                    }
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
    Text(
        text = desc,
        modifier = Modifier.fillMaxWidth(),
    )
}
