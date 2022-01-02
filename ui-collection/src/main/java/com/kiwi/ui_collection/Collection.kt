package com.kiwi.ui_collection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.KiwisBarTheme
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.Cocktail
import com.kiwi.data.entities.Favorite


@Composable
fun Collection(
    viewModel: CollectionViewModel = hiltViewModel(),
    openRecipe: () -> Unit,
) {

    val lazyPagingItems = rememberFlowWithLifecycle(viewModel.pagedList).collectAsLazyPagingItems()
    val isEmpty = lazyPagingItems.itemCount == 0

    Column(Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .statusBarsPadding()
        ) {
            collectionTitle()

            if (!isEmpty) {
                collectionList(
                    list = lazyPagingItems,
                    onItemClick = openRecipe
                )
            }
        }
        if (isEmpty) {
            Empty(modifier = Modifier.weight(1f))
        }
    }
}

private fun LazyListScope.collectionTitle() {
    item {
        Text(
            text = "Menu",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.collectionList(
    list: LazyPagingItems<Favorite>,
    onItemClick: () -> Unit
) {
    var lastFavorite: Favorite? = null
    for (index in list.itemSnapshotList.indices) {
        val favorite = list.peek(index)
        if (lastFavorite?.catalog != favorite?.catalog) {
            stickyHeader {
                SectionHeader(favorite!!.catalog)
            }
        }

        item {
            CollectionItem(
                cocktail = favorite!!.cocktail,
                onClick = onItemClick
            )
        }

        lastFavorite = favorite
    }
}

@Composable
private fun SectionHeader(sectionName: String) {
    Text(
        text = sectionName,
        Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
            .fillMaxSize()
    )
}

@Composable
private fun CollectionItem(
    cocktail: Cocktail,
    onClick: () -> Unit,
) {

    val ingredients = rememberSaveable {
        cocktail.ingredients.joinToString(prefix = "(", postfix = ")") { it.name }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = cocktail.name,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = ingredients
        )
    }
}

@Composable
private fun Empty(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("animations/59196-drink.json"))
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        LottieAnimation(
            composition = composition,
            contentScale = ContentScale.Crop,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier
                .scale(2f)
                .size(200.dp)
        )
        Text(
            textAlign = TextAlign.Center,
            text = "目前這邊空空，要不要現在製作自己的酒單？",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        )
        Button(onClick = { /*TODO*/ }) {
            Text(text = "瀏覽酒譜")
        }
    }
}

@Preview
@Composable
private fun OnboardingPreview() {
    KiwisBarTheme {
        Surface {
//            Collection({})
        }
    }
}
