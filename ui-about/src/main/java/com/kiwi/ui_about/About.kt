package com.kiwi.ui_about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun About() {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Title()
        AboutItem(
            text = stringResource(id = R.string.feed_back),
            painter = rememberVectorPainter(Icons.Default.Edit),
            onItemClick = { /*TODO*/ }
        )
        Divider()
        AboutItem(
            text = stringResource(id = R.string.licenses),
            painter = painterResource(id = R.drawable.ic_baseline_copyright_24),
            onItemClick = { /*TODO*/ }
        )
    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(R.string.about_title),
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    )
}

@Composable
fun AboutItem(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    onItemClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(onClick = onItemClick)
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 24.dp),
            text = text
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_navigate_next_24),
            contentDescription = null
        )
    }
}

@Preview
@Composable
private fun PreviewAbout() {
    About()
}

@Preview
@Composable
private fun PreviewAboutItem() {
    AboutItem(
        text = "Text",
        painter = rememberVectorPainter(Icons.Default.Edit),
        onItemClick = {},
    )
}
