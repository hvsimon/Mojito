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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.google.accompanist.insets.statusBarsPadding

@Preview
@Composable
fun Onboarding() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
    ) {
        Header(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.75f)
        )
        Text(
            text = "六大基酒",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(16.dp)
        )
        BaseWineSection()
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Image(
            painter = rememberImagePainter(
                data = "https://images.unsplash.com/photo-1513558161293-cdaf765ed2fd?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80",
                builder = {
                    size(OriginalSize)
                },
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
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
                text = "莫希托 (Mojito)",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "莫希托（西班牙語：Mojito、西班牙語發音：[moˈxito]）是一種傳統的古巴高球雞尾酒。傳統上，莫希托是一種由五種材料製成的雞尾酒：淡蘭姆酒、白砂糖（傳統上是用甘蔗汁）、青檸汁、蘇打水和薄荷。最原始的古巴配方是使用留蘭香或古巴島上常見的檸檬薄荷。[1]萊姆與薄荷的清爽口味是為了與蘭姆酒的烈性相互補，同時也使得這種透明無色的調酒成為夏日的熱門飲料之一。[2]這種調酒有著相對低的酒精含量(大約10%)。",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Button(
                onClick = { },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(text = "好手氣")
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
        Text(text = "想喝什麼呢 ?")
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
            "https://images.unsplash.com/photo-1614313511387-1436a4480ebb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1760&q=80",
            "https://images.unsplash.com/photo-1608885898957-a559228e8749?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80",
            "https://images.unsplash.com/photo-1550985543-f47f38aeee65?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1335&q=80",
            "https://images.unsplash.com/photo-1516535794938-6063878f08cc?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=988&q=80",
            "https://images.unsplash.com/photo-1527281400683-1aae777175f8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=987&q=80",
            "https://images.unsplash.com/photo-1619451050621-83cb7aada2d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=986&q=80",
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp),
        ) {
            WineCard(
                imageData = wineList[0],
                wineName = "蘭姆酒",
                modifier = Modifier.weight(1f),
            )
            WineCard(
                imageData = wineList[1],
                wineName = "琴酒",
                modifier = Modifier.weight(1f),
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp),
        ) {
            WineCard(
                imageData = wineList[2],
                wineName = "伏特加",
                modifier = Modifier.weight(1f),
            )
            WineCard(
                imageData = wineList[3],
                wineName = "龍舌蘭",
                modifier = Modifier.weight(1f),
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp),
        ) {
            WineCard(
                imageData = wineList[4],
                wineName = "威士忌",
                modifier = Modifier.weight(1f),
            )
            WineCard(
                imageData = wineList[5],
                wineName = "白蘭地",
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