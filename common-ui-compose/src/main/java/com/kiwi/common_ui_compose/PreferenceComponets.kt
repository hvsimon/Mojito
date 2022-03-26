package com.kiwi.common_ui_compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    subTitle: String? = null,
    enable: Boolean = true,
    action: @Composable (() -> Unit)? = null,
    divider: @Composable (() -> Unit)? = { Divider() },
    onClick: () -> Unit,
) {
    val enableColor = if (enable) {
        MaterialTheme.colorScheme.onSurface
    } else {
        MaterialTheme.colorScheme.onSurface
            .copy(alpha = ContentAlpha.disabled)
            .compositeOver(MaterialTheme.colorScheme.surface)
    }

    Column(
        modifier = modifier
            .clickable(onClick = onClick)
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
                painter = icon,
                contentDescription = null,
                tint = enableColor,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = title,
                    color = enableColor,
                )
                subTitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = enableColor,
                    )
                }
            }
            action?.run { this() }
        }
        divider?.run { this() }
    }
}

@Composable
fun NavigationPreference(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    subTitle: String? = null,
    enable: Boolean = true,
    divider: @Composable (() -> Unit)? = { Divider() },
    onClick: () -> Unit,
) {
    Preference(
        modifier = modifier,
        icon = icon,
        title = title,
        subTitle = subTitle,
        enable = enable,
        action = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_navigate_next_24),
                contentDescription = null
            )
        },
        divider = divider,
        onClick = onClick,
    )
}

@Composable
fun SwitchPreference(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    subTitle: String? = null,
    enable: Boolean = true,
    checked: Boolean,
    divider: @Composable (() -> Unit)? = { Divider() },
    onCheckedChange: (Boolean) -> Unit,
) {
    Preference(
        modifier = modifier,
        icon = icon,
        title = title,
        subTitle = subTitle,
        enable = enable,
        action = {
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                enabled = enable,
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
        },
        divider = divider,
        onClick = { onCheckedChange(!checked) },
    )
}
