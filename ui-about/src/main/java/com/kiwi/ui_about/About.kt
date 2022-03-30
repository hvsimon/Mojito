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
    openBrowsingHistory: () -> Unit,
    openLicenses: () -> Unit,
) {
    About(
        viewModel = hiltViewModel(),
        openBrowsingHistory = openBrowsingHistory,
        openLicenses = openLicenses,
    )
}

@Composable
private fun About(
    viewModel: AboutViewModel,
    openBrowsingHistory: () -> Unit,
    openLicenses: () -> Unit,
) {

    val uiState by rememberStateWithLifecycle(viewModel.uiState)

    About(
        uiState = uiState,
        openBrowsingHistory = openBrowsingHistory,
        openLicenses = openLicenses,
        onDeviceThemeChange = { viewModel.setDeviceTheme(it) },
        onDynamicColorsEnableChange = { viewModel.enableDynamicColors(it) },
    )
}

@Composable
private fun About(
    uiState: AboutUiState,
    openBrowsingHistory: () -> Unit,
    openLicenses: () -> Unit,
    onDeviceThemeChange: (DeviceTheme) -> Unit,
    onDynamicColorsEnableChange: (Boolean) -> Unit,
) {
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        val context = LocalContext.current

        Title()

        NavigationPreference(
            icon = painterResource(id = R.drawable.ic_baseline_history_24),
            title = stringResource(id = R.string.browsing_history),
            onClick = openBrowsingHistory,
        )

        SectionTitle(stringResource(id = R.string.appearance))

        val options = stringArrayResource(id = R.array.setting_theme_options)
        val systemDefault = stringResource(id = R.string.system_default)
        val lightTheme = stringResource(id = R.string.light_theme)
        val darkTheme = stringResource(id = R.string.dark_theme)

        DropdownMenuPreference(
            icon = painterResource(id = R.drawable.ic_baseline_contrast_24),
            title = stringResource(id = R.string.setting_theme),
            subtitle = when (uiState.deviceTheme) {
                DeviceTheme.SYSTEM -> systemDefault
                DeviceTheme.LIGHT -> lightTheme
                DeviceTheme.DARK -> darkTheme
                else -> ""
            },
            options = options,
        ) { index ->
            val deviceTheme = when (options[index]) {
                systemDefault -> DeviceTheme.SYSTEM
                lightTheme -> DeviceTheme.LIGHT
                darkTheme -> DeviceTheme.DARK
                else -> return@DropdownMenuPreference
            }
            onDeviceThemeChange(deviceTheme)
        }

        SwitchPreference(
            icon = painterResource(id = R.drawable.ic_baseline_palette_24),
            title = stringResource(id = R.string.enable_dynamic_color),
            subtitle = stringResource(id = R.string.dynamic_color_hint),
            enable = Build.VERSION.SDK_INT >= 31,
            checked = uiState.enableDynamicColors,
            onCheckedChange = onDynamicColorsEnableChange,
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
        openBrowsingHistory = {},
        openLicenses = {},
        onDeviceThemeChange = {},
        onDynamicColorsEnableChange = {},
    )
}

@Preview
@Composable
fun PreviewVersionItem() {
    VersionItem(versionName = "1.0", versionCode = 1)
}
