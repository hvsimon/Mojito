package com.kiwi.ui_about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.PackageInfoCompat
import com.google.accompanist.insets.statusBarsPadding

@Composable
fun About(
    openLicenses: () -> Unit,
) {
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
        AboutItem(
            text = stringResource(id = R.string.licenses),
            painter = painterResource(id = R.drawable.ic_baseline_copyright_24),
            onItemClick = openLicenses
        )

        val packageInfo = LocalContext.current
            .packageManager
            .getPackageInfo(LocalContext.current.packageName, 0)

        VersionItem(
            versionName = packageInfo.versionName,
            versionCode = PackageInfoCompat.getLongVersionCode(packageInfo),
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
private fun AboutItem(
    modifier: Modifier = Modifier,
    text: String,
    painter: Painter,
    onItemClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .clickable(onClick = onItemClick)
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 4.dp)
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
        Divider()
    }
}

@Composable
private fun VersionItem(
    modifier: Modifier = Modifier,
    versionName: String,
    versionCode: Long,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource(
                id = R.string.settings_app_version_summary,
                versionName,
                versionCode
            )
        )
    }
}

@Preview
@Composable
private fun PreviewAbout() {
    About(
        openLicenses = {}
    )
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

@Preview
@Composable
fun PreviewVersionItem() {
    VersionItem(versionName = "1.0", versionCode = 1)
}
