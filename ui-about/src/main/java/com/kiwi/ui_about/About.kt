package com.kiwi.ui_about

import android.content.Intent
import android.os.Build
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.kiwi.common_ui_compose.rememberStateWithLifecycle

@Composable
fun About(
    openLicenses: () -> Unit,
) {
    About(
        viewModel = hiltViewModel(),
        openLicenses = openLicenses,
    )
}

@Composable
private fun About(
    viewModel: AboutViewModel,
    openLicenses: () -> Unit,
) {

    val uiState by rememberStateWithLifecycle(viewModel.uiState)

    About(
        uiState = uiState,
        openLicenses = openLicenses,
    )
}

@Composable
private fun About(
    uiState: AboutUiState,
    openLicenses: () -> Unit,
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        val email = stringResource(id = R.string.contact_email)
        val subject = stringResource(id = R.string.feedback_subject)

        Title()
        SectionTitle(stringResource(id = R.string.theme))

        var checked by remember { mutableStateOf(false) }
        SwitchableItem(
            text = stringResource(id = R.string.enable_dynamic_color),
            caption = stringResource(id = R.string.dynamic_color_hint),
            painter = painterResource(id = R.drawable.ic_baseline_palette_24),
            checked = checked,
            enabled = Build.VERSION.SDK_INT >= 31,
            onCheckedChange = { checked = it },
        )

        SectionTitle(stringResource(id = R.string.support))
        AboutItem(
            text = stringResource(id = R.string.feedback),
            painter = rememberVectorPainter(Icons.Default.Edit),
            onItemClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = "mailto:".toUri()
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                }
                startActivity(context, Intent.createChooser(intent, ""), null)
            }
        )
        SectionTitle(stringResource(id = R.string.legal))
        AboutItem(
            text = stringResource(id = R.string.privacy_policy),
            painter = painterResource(id = R.drawable.ic_baseline_menu_book_24),
            onItemClick = {
                CustomTabsIntent.Builder()
                    .build()
                    .launchUrl(context, "https://hvsimon.github.io/Mojito/privacypolicy".toUri())
            }
        )
        AboutItem(
            text = stringResource(id = R.string.licenses),
            painter = painterResource(id = R.drawable.ic_baseline_copyright_24),
            onItemClick = openLicenses
        )

        VersionItem(
            versionName = uiState.versionName,
            versionCode = uiState.versionCode,
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
private fun SectionTitle(sectionTitle: String) {
    Text(
        text = sectionTitle,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp)
    )
}

@Composable
private fun SwitchableItem(
    modifier: Modifier = Modifier,
    text: String,
    caption: String,
    painter: Painter,
    checked: Boolean,
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {

    val enableColor = if (enabled) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface
            .copy(alpha = ContentAlpha.disabled)
            .compositeOver(MaterialTheme.colorScheme.surface)
    }

    Column(
        modifier = modifier
            .clickable(onClick = { onCheckedChange(!checked) })
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
                contentDescription = null,
                tint = enableColor,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = text,
                    color = enableColor,
                )
                Text(
                    text = caption,
                    style = MaterialTheme.typography.bodySmall,
                    color = enableColor,
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enabled,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                    uncheckedTrackColor = MaterialTheme.colorScheme.onSurface,
                    disabledCheckedThumbColor = MaterialTheme.colorScheme.primary
                        .copy(alpha = ContentAlpha.disabled)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                    disabledCheckedTrackColor = MaterialTheme.colorScheme.primary
                        .copy(alpha = ContentAlpha.disabled)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                    disabledUncheckedThumbColor = MaterialTheme.colorScheme.surface
                        .copy(alpha = ContentAlpha.disabled)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                    disabledUncheckedTrackColor = MaterialTheme.colorScheme.onSurface
                        .copy(alpha = ContentAlpha.disabled)
                        .compositeOver(MaterialTheme.colorScheme.surface),
                )
            )
        }
        Divider()
    }
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
        uiState = AboutUiState("1.0.0", 99),
        openLicenses = {},
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
