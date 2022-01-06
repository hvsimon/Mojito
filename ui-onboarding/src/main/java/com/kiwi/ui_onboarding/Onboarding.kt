package com.kiwi.ui_onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.google.accompanist.insets.statusBarsPadding
import com.kiwi.common_ui_compose.KiwisBarTheme
import com.kiwi.common_ui_compose.rememberFlowWithLifecycle
import com.kiwi.data.entities.Cocktail

@Composable
fun Onboarding(
    viewModel: OnboardingViewModel = hiltViewModel(),
    openRecipe: (cocktailId: Long) -> Unit,
) {

    val uiState by rememberFlowWithLifecycle(viewModel.uiState).collectAsState(initial = OnboardingUiState())

    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        uiState.coverCocktail?.let { cocktail ->
            Header(
                cocktail = cocktail,
                onRandomClick = openRecipe,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(fraction = 0.75f)
            )
        }
        Text(
            text = stringResource(id = R.string.six_base_wine),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )
        BaseWineSection()
    }
}

@Composable
fun Header(
    cocktail: Cocktail,
    onRandomClick: (cocktailId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = rememberImagePainter(
                data = cocktail.gallery.random(),
                builder = {
                    size(OriginalSize)
                },
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black),
                    )
                )
                .padding(16.dp)
        ) {
            Text(
                text = cocktail.name,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = cocktail.intro,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Button(
                onClick = { onRandomClick(cocktail.cocktailId) },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.flexible))
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = { },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Icon(imageVector = Icons.Default.Search, contentDescription = null)
        Text(text = stringResource(id = R.string.drink_something))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BaseWineSection() {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val wineList = listOf(
            "https://images.unsplash.com/photo-1614313511387-1436a4480ebb?ixlib=rb-1.2.1&ixid=Mnw" +
                "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1760&q=80",
            "https://images.unsplash.com/photo-1608885898957-a559228e8749?ixlib=rb-1.2.1&ixid=Mnw" +
                "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80",
            "https://images.unsplash.com/photo-1550985543-f47f38aeee65?ixlib=rb-1.2.1&ixid=MnwxMj" +
                "A3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1335&q=80",
            "https://images.unsplash.com/photo-1516535794938-6063878f08cc?ixlib=rb-1.2.1&ixid=Mnw" +
                "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=988&q=80",
            "https://images.unsplash.com/photo-1527281400683-1aae777175f8?ixlib=rb-1.2.1&ixid=Mnw" +
                "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80",
            "https://images.unsplash.com/photo-1619451050621-83cb7aada2d7?ixlib=rb-1.2.1&ixid=Mnw" +
                "xMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=986&q=80",
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp),
        ) {
            WineCard(
                imageData = wineList[0],
                wineName = stringResource(id = R.string.rum),
                modifier = Modifier.weight(1f),
            )
            WineCard(
                imageData = wineList[1],
                wineName = stringResource(id = R.string.gin),
                modifier = Modifier.weight(1f),
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp),
        ) {
            WineCard(
                imageData = wineList[2],
                wineName = stringResource(id = R.string.vodka),
                modifier = Modifier.weight(1f),
            )
            WineCard(
                imageData = wineList[3],
                wineName = stringResource(id = R.string.tequila),
                modifier = Modifier.weight(1f),
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp),
        ) {
            WineCard(
                imageData = wineList[4],
                wineName = stringResource(id = R.string.whiskey),
                modifier = Modifier.weight(1f),
            )
            WineCard(
                imageData = wineList[5],
                wineName = stringResource(id = R.string.brandy),
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WineCard(
    imageData: Any,
    wineName: String,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = {},
        modifier = modifier,
    ) {
        Box {
            Image(
                painter = rememberImagePainter(
                    data = imageData,
                    builder = {
                        size(OriginalSize)
                    },
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black.copy(alpha = 0.5f))
            ) {
                Text(
                    text = wineName,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
        }
    }
}

@Preview
@Composable
fun OnboardingPreview() {
    KiwisBarTheme {
        Surface {
//            Onboarding({}, {})
        }
    }
}