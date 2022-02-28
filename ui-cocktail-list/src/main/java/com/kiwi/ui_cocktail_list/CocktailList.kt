package com.kiwi.ui_cocktail_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.data.entities.Cocktail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailList(
    viewModel: CocktailListViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
) {
    Scaffold(
        topBar = {
            CocktailListAppBar(
                title = "TODO",
                navigateUp = navigateUp,
            )
        },
    ) {
        CocktailList(
            list = viewModel.list,
            onItemClick = openRecipe,
        )
    }
}

@Composable
private fun CocktailListAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigateUp: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    SmallTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier.statusBarsPadding(),
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CocktailList(
    list: List<Cocktail>,
    onItemClick: (cocktailId: String) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(list) { cocktail ->
            CocktailCard(
                cocktail = cocktail,
                onClick = onItemClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CocktailCard(
    cocktail: Cocktail,
    onClick: (cocktailId: String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick.invoke(cocktail.cocktailId) }),
    ) {
        Column {
            Image(
                painter = rememberImagePainter(
                    data = cocktail.gallery.randomOrNull(),
                    builder = {
                        placeholder(R.drawable.ic_mojito_filled)
                    },
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(194.dp)
                    .background(Color.Gray)
            )

            Text(
                text = cocktail.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCocktailListAppBar() {
    CocktailListAppBar(
        title = "Rum",
        navigateUp = { /*TODO*/ },
    )
}

@Preview
@Composable
fun PreviewCocktailGrid() {
    val list = MutableList(8) {
        Cocktail(
            name = "Cocktail Name",
            gallery = listOf("https://images.unsplash.com/photo-1609345265499-2133bbeb6ce5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1994&q=80"),
            intro = "",
            ingredients = emptyList(),
            steps = emptyList(),
            tips = emptySet(),
        )
    }
    CocktailList(
        list = list,
        onItemClick = {},
    )
}

@Preview
@Composable
fun PreviewCocktailCard() {
    CocktailCard(
        cocktail = Cocktail(
            name = "Cocktail Name",
            gallery = listOf("https://images.unsplash.com/photo-1609345265499-2133bbeb6ce5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1994&q=80"),
            intro = "",
            ingredients = emptyList(),
            steps = emptyList(),
            tips = emptySet(),
        ),
        onClick = {},
    )
}