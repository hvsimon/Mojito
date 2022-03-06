package com.kiwi.ui_cocktail_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.CocktailPo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailList(
    viewModel: CocktailListViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    openRecipe: (cocktailId: String) -> Unit,
) {
    val cocktails by rememberFlowWithLifecycle(viewModel.list).collectAsState(emptyList())
    val scrollBehavior = remember { pinnedScrollBehavior() }

    Scaffold(
        topBar = {
            CocktailListAppBar(
                title = viewModel.groupName.uppercase(),
                navigateUp = navigateUp,
                scrollBehavior = scrollBehavior,
            )
        },
        modifier = Modifier
            .statusBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) {
        CocktailList(
            list = cocktails,
            onItemClick = openRecipe,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
        modifier = modifier,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun CocktailList(
    list: List<CocktailPo>,
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
    cocktail: CocktailPo,
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
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCocktailListAppBar() {
    CocktailListAppBar(
        title = "Title",
        navigateUp = { },
    )
}

@Composable
private fun SectionHeader(sectionName: String) {
    Text(
        text = sectionName,
        Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Preview
@Composable
private fun PreviewSectionHeader() {
    SectionHeader(sectionName = "Section Name")
}

@Preview
@Composable
fun PreviewCocktailGrid() {
    val list = MutableList(8) {
        CocktailPo(
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
        cocktail = CocktailPo(
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