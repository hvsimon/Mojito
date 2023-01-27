package com.kiwi.ui_browsing_history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.size.Precision
import coil.transform.RoundedCornersTransformation
import com.kiwi.common_ui_compose.CommonAppBar
import com.kiwi.data.entities.BrowsingHistoryAndCocktail
import com.kiwi.data.entities.BrowsingHistoryEntity
import com.kiwi.data.entities.FullDrinkEntity

@Composable
fun BrowsingHistory(
    viewModel: BrowsingHistoryViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    openRecipe: (String) -> Unit,
) {
    val data = viewModel.pagedList.collectAsLazyPagingItems()

    BrowsingHistoryList(
        navigateUp = navigateUp,
        lazyPagingItems = data,
        onItemClick = openRecipe,
        onItemClearClick = { viewModel.clearHistory(it) },
        onClearAllClick = { viewModel.clearAll() },
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
private fun BrowsingHistoryList(
    navigateUp: () -> Unit,
    lazyPagingItems: LazyPagingItems<BrowsingHistoryAndCocktail>,
    onItemClick: (cocktailId: String) -> Unit,
    onItemClearClick: (BrowsingHistoryEntity) -> Unit,
    onClearAllClick: () -> Unit,
) {

    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    val showClearAllCheckDialog = rememberSaveable { mutableStateOf(false) }

    val isEmpty = lazyPagingItems.loadState.refresh is LoadState.NotLoading &&
        lazyPagingItems.loadState.append.endOfPaginationReached &&
        lazyPagingItems.itemCount == 0

    if (showClearAllCheckDialog.value) {
        AlertDialog(
            onDismissRequest = { showClearAllCheckDialog.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearAllClick()
                        showClearAllCheckDialog.value = false
                    }
                ) {
                    Text(stringResource(id = R.string.clear_all))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showClearAllCheckDialog.value = false
                    }
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            },
            title = { Text(text = stringResource(id = R.string.clear_all_check_title)) },
            text = { Text(text = stringResource(id = R.string.clear_all_check_message)) },
        )
    }

    Scaffold(
        topBar = {
            CommonAppBar(
                title = stringResource(id = R.string.browsing_history),
                navigateUp = navigateUp,
                scrollBehavior = scrollBehavior,
                actions = {
                    TextButton(onClick = { showClearAllCheckDialog.value = true }) {
                        Text(text = stringResource(id = R.string.clear_all))
                    }
                }
            )
        },
        modifier = Modifier
            .statusBarsPadding()
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->

        if (isEmpty) EmptyLayout()

        LazyColumn(
            contentPadding = paddingValues,
        ) {
            items(
                items = lazyPagingItems,
                key = { it.browsingHistoryEntity.cocktailId },
            ) { item ->
                if (item == null) {
                    // show placeholder
                } else {
                    BrowsingHistoryItem(
                        modifier = Modifier.animateItemPlacement(),
                        cocktail = item.cocktail,
                        onClick = { onItemClick(item.cocktail.id) },
                        onClear = { onItemClearClick(item.browsingHistoryEntity) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BrowsingHistoryItem(
    modifier: Modifier = Modifier,
    cocktail: FullDrinkEntity,
    onClick: () -> Unit,
    onClear: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = cocktail.thumb,
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
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = cocktail.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = cocktail.ingredients.joinToString(prefix = "(", postfix = ")") { it },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        IconButton(onClick = onClear) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun EmptyLayout() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        // TODO: Find a beautiful image
//        Image(
//            painter = painterResource(),
//            contentDescription = null,
//            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
//            modifier = Modifier.size(200.dp)
//        )
        Text(text = stringResource(id = R.string.browsing_history_empty))
    }
}
