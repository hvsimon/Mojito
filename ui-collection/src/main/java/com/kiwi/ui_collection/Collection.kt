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
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.CocktailPo
import com.kiwi.data.entities.Ingredient
import java.util.UUID

@Composable
fun Collection(
    viewModel: CollectionViewModel = hiltViewModel(),
    openRecipe: (cocktailId: String) -> Unit,
) {
    val lazyPagingItems = rememberFlowWithLifecycle(viewModel.pagedList).collectAsLazyPagingItems()
    val isEmpty = lazyPagingItems.itemCount == 0

    Collection(
        lazyPagingItems = lazyPagingItems,
        isEmpty = isEmpty,
        openRecipe = openRecipe,
    )
}

@Composable
private fun Collection(
    lazyPagingItems: LazyPagingItems<UiModel>,
    isEmpty: Boolean,
    openRecipe: (cocktailId: String) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.statusBarsPadding()
        ) {
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
            Empty(modifier = Modifier.weight(1f))
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
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick)
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
            text = stringResource(R.string.follow_empty_message),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        )
        Button(onClick = { /*TODO*/ }) {
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
private fun PreviewCollectionItem() {
    CollectionItem(
        cocktail = CocktailPo(
            cocktailId = UUID.randomUUID().toString(),
            name = "Mojito2",
            gallery = listOf("https://images.unsplash.com/photo-1609345265499-2133bbeb6ce5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1994&q=80"),
            intro = "The refreshingly minty-citrus flavours of Cuba's Mojito cocktail perfectly compliment white Rum & is wonderful on a hot summer's day. Highly popular, yet tricky to get right & often served too sweet - the key to a good Mojito is to use plenty of fresh Mint & Lime juice but not too much Sugar.",
            ingredients = listOf(
                Ingredient(
                    name = "\uD83C\uDF78 白蘭姆酒",
                    amount = "2 shots"
                ),
                Ingredient(
                    name = "\uD83E\uDDC2 糖漿",
                    amount = "0.5 shot"
                ),
            ),
            steps = listOf(
                "1. Place the Mint, Sugar Syrup & Lime wedges into a highball glass & lightly muddle the ingredients together. The Lime wedges & Mint leaves should be bruised to release their juices & essential oils.",
                "2. Fill the glass with crushed ice, pour over the White Rum & stir.",
                "3. Top up with Soda Water & stir well from the bottom up.",
                "4. Garnish with a sprig of Mint & serve with a straw.",
            ),
            tips = setOf(
                "Given that the Mojito is reliant on fresh Limes & Mint that are available in different sizes & flavour intensity, it is a cocktail that really rewards you if you tune the quantities to accommodate the ingredients you have available & most importantly, your own personal tastes.",
                "If you make a Mojito & find it too sweet, you could try adding Angostura bitters to cut down on the sweetness.",
                "Don't have any Limes to hand? Try using Lemons for a twist on the classic Mojito but note that you might need to add a little more Sugar Syrup or use a little less Lemon to balance out the extra sourness.",
                "If you're out of Sugar syrup, you can use half a teaspoon of fine caster Sugar instead. If using Sugar, you might like to add the White Rum & stir to dissolve the Sugar before adding the crushed Ice.",
                "Don't have crushed ice? Originally, Cuban bartenders mixed the drink using cubed ice, including mint stalks as well as leaves - try this for an equally tasty yet more rustic feel.",
            )
        ),
        onClick = {}
    )
}
