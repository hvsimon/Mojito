package com.kiwi.ui_collection

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import coil.size.Precision
import coil.transform.RoundedCornersTransformation
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.SampleCocktailPoProvider
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.CocktailPo

@Composable
fun Collection(
    viewModel: CollectionViewModel = hiltViewModel(),
    openRecipe: (cocktailId: String) -> Unit,
    openSearch: () -> Unit,
) {
    val lazyPagingItems = rememberFlowWithLifecycle(viewModel.pagedList).collectAsLazyPagingItems()
    val isEmpty = lazyPagingItems.itemCount == 0

    Collection(
        lazyPagingItems = lazyPagingItems,
        isEmpty = isEmpty,
        openRecipe = openRecipe,
        openSearch = openSearch,
    )
}

@Composable
private fun Collection(
    lazyPagingItems: LazyPagingItems<UiModel>,
    isEmpty: Boolean,
    openRecipe: (cocktailId: String) -> Unit,
    openSearch: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        LazyColumn {
            item {
                CollectionTitle()
            }

            if (!isEmpty) {
                collectionList(
                    list = lazyPagingItems,
                    onItemClick = openRecipe
                )
            }
        }
        if (isEmpty) {
            Empty(
                modifier = Modifier.weight(1f),
                onButtonClick = openSearch,
            )
        }
    }
}

@Composable
private fun CollectionTitle() {
    Text(
        text = stringResource(R.string.follow_title),
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
private fun LazyListScope.collectionList(
    list: LazyPagingItems<UiModel>,
    onItemClick: (cocktailId: String) -> Unit
) {
    for (index in list.itemSnapshotList.indices) {
        when (val uiModel = list.peek(index)) {
            is UiModel.HeaderModel -> {
                val catalogName = uiModel.name
                this@collectionList.stickyHeader {
                    SectionHeader(catalogName)
                }
            }
            is UiModel.FollowedModel -> {
                val cocktail = uiModel.cocktail
                item {
                    CollectionItem(
                        cocktail = cocktail,
                        onClick = { onItemClick(cocktail.cocktailId) }
                    )
                }
            }
            null -> {}
        }
    }
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

@Composable
private fun CollectionItem(
    cocktail: CocktailPo,
    onClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = cocktail.gallery.firstOrNull(),
                builder = {
                    crossfade(true)
                    transformations(RoundedCornersTransformation(radius = 16.dp.value))
                    placeholder(R.drawable.ic_cat)
                    precision(Precision.EXACT)
                },
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.size(56.dp)
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = cocktail.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = cocktail.ingredients.joinToString(prefix = "(", postfix = ")") { it.name },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun Empty(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("animations/59196-drink.json")
    )
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
            text = stringResource(R.string.follow_empty_message),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        )
        Button(onClick = onButtonClick) {
            Text(text = stringResource(R.string.browse_cocktails))
        }
    }
}

@Preview
@Composable
fun PreviewCollectionTitle() {
    CollectionTitle()
}

@Preview
@Composable
private fun PreviewSectionHeader() {
    SectionHeader(sectionName = "Section Name")
}

@Preview
@Composable
private fun PreviewCollectionItem(
    @PreviewParameter(SampleCocktailPoProvider::class) cocktailPo: CocktailPo
) {
    CollectionItem(
        cocktail = cocktailPo,
        onClick = {}
    )
}
