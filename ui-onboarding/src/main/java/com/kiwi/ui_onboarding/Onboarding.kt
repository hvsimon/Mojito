package com.kiwi.ui_onboarding

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Onboarding(
    viewModel: OnboardingViewModel = hiltViewModel(),
    openRecipe: (cocktailId: Long) -> Unit,
) {

    val uiState by rememberFlowWithLifecycle(viewModel.uiState).collectAsState(initial = OnboardingUiState())

    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        item(span = { GridItemSpan(2) }) {
            Header(
                cocktail = uiState.coverCocktail,
                onRandomClick = openRecipe,
                modifier = Modifier.fillParentMaxHeight(0.75f)
            )
        }
        item(span = { GridItemSpan(2) }) {
            Text(
                text = stringResource(id = R.string.six_base_wine),
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(uiState.baseWines) {
            WineCard(
                imageData = it.imageUrl,
                wineName = it.id,
            )
        }
    }
}

@Composable
fun Header(
    cocktail: Cocktail?,
    onRandomClick: (cocktailId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = rememberImagePainter(
                data = cocktail?.gallery?.random(),
                builder = {
                    size(OriginalSize)
                    crossfade(true)
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
        cocktail?.let { cocktail ->
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WineCard(
    imageData: Any,
    wineName: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Card(
        onClick = { Toast.makeText(context, wineName, Toast.LENGTH_SHORT).show() },
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f),
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
            Onboarding(
                openRecipe = {}
            )
        }
    }
}
