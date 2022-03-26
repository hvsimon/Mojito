package com.kiwi.ui_about

import android.content.Intent
import android.os.Build
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.kiwi.common_ui_compose.DropdownMenuPreference
import com.kiwi.common_ui_compose.NavigationPreference
import com.kiwi.common_ui_compose.SwitchPreference
import com.kiwi.common_ui_compose.rememberStateWithLifecycle
import com.kiwi.data.entities.DeviceTheme

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

        Title()

        SectionTitle(stringResource(id = R.string.appearance))

        DropdownMenuPreference(
            icon = painterResource(id = R.drawable.ic_baseline_contrast_24),
            title = stringResource(id = R.string.setting_theme),
            subtitle = when (uiState.currentDeviceTheme) {
                DeviceTheme.SYSTEM -> stringResource(id = R.string.system_default)
                DeviceTheme.LIGHT -> stringResource(id = R.string.light_theme)
                DeviceTheme.DARK -> stringResource(id = R.string.dark_theme)
                else -> ""
            },
            options = stringArrayResource(id = R.array.setting_theme_options),
        ) {
            // TODO: update theme
        }

        var checked by remember { mutableStateOf(false) }
        SwitchPreference(
            icon = painterResource(id = R.drawable.ic_baseline_palette_24),
            title = stringResource(id = R.string.enable_dynamic_color),
            subtitle = stringResource(id = R.string.dynamic_color_hint),
            enable = Build.VERSION.SDK_INT >= 31,
            checked = checked,
            onCheckedChange = { checked = it },
        )

        SectionTitle(stringResource(id = R.string.support))

        val email = stringResource(id = R.string.contact_email)
        val subject = stringResource(id = R.string.feedback_subject)
        NavigationPreference(
            icon = rememberVectorPainter(Icons.Default.Edit),
            title = stringResource(id = R.string.feedback),
        ) {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = "mailto:".toUri()
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
            }
            startActivity(context, Intent.createChooser(intent, ""), null)
        }

        SectionTitle(stringResource(id = R.string.legal))
        NavigationPreference(
            icon = painterResource(id = R.drawable.ic_baseline_menu_book_24),
            title = stringResource(id = R.string.privacy_policy),
        ) {
            CustomTabsIntent.Builder()
                .build()
                .launchUrl(context, "https://hvsimon.github.io/Mojito/privacypolicy".toUri())
        }

        NavigationPreference(
            icon = painterResource(id = R.drawable.ic_baseline_copyright_24),
            title = stringResource(id = R.string.licenses),
            onClick = openLicenses,
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
fun PreviewVersionItem() {
    VersionItem(versionName = "1.0", versionCode = 1)
}
