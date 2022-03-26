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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Composable
fun Preference(
    modifier: Modifier = Modifier,
    icon: Painter,
    iconTint: Color = LocalContentColor.current,
    disabledIconTint: Color = iconTint
        .copy(alpha = ContentAlpha.disabled)
        .compositeOver(MaterialTheme.colorScheme.surface),
    title: String,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    disabledTitleColor: Color = titleColor
        .copy(alpha = ContentAlpha.disabled)
        .compositeOver(MaterialTheme.colorScheme.surface),
    subtitle: String? = null,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurface,
    disabledSubtitleColor: Color = subtitleColor
        .copy(alpha = ContentAlpha.disabled)
        .compositeOver(MaterialTheme.colorScheme.surface),
    enable: Boolean = true,
    action: @Composable (() -> Unit)? = null,
    divider: @Composable (() -> Unit)? = {
        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f))
    },
    onClick: () -> Unit,
) {
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
                tint = if (enable) iconTint else disabledIconTint,
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = title,
                    color = if (enable) titleColor else disabledTitleColor,
                )
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (enable) subtitleColor else disabledSubtitleColor,
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
    subtitle: String? = null,
    enable: Boolean = true,
    divider: @Composable (() -> Unit)? = {
        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f))
    },
    onClick: () -> Unit,
) {
    Preference(
        modifier = modifier,
        icon = icon,
        title = title,
        subtitle = subtitle,
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
    subtitle: String? = null,
    enable: Boolean = true,
    checked: Boolean,
    divider: @Composable (() -> Unit)? = {
        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f))
    },
    onCheckedChange: (Boolean) -> Unit,
) {
    Preference(
        modifier = modifier,
        icon = icon,
        title = title,
        subtitle = subtitle,
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

@Composable
fun DropdownMenuPreference(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    subtitle: String? = null,
    enable: Boolean = true,
    options: Array<String>,
    divider: @Composable (() -> Unit)? = {
        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.12f))
    },
    onOptionSelected: (index: Int) -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }

    Preference(
        modifier = modifier,
        icon = icon,
        title = title,
        subtitle = subtitle,
        enable = enable,
        action = {
            if (enable) {
                DropdownMenu(
                    expanded = expanded,
                    offset = DpOffset(x = 48.dp, y = 0.dp),
                    onDismissRequest = { expanded = false },
                ) {
                    options.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            text = { Text(s) },
                            onClick = {
                                onOptionSelected(index)
                                expanded = false
                            },
                        )
                    }
                }
            }
        },
        divider = divider,
        onClick = {
            if (enable) {
                expanded = true
            }
        },
    )
}
