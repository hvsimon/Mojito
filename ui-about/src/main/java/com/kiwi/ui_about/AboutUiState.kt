package com.kiwi.ui_about

import com.kiwi.data.entities.DeviceTheme

internal data class AboutUiState(
    val versionName: String = "",
    val versionCode: Long = 0,
    val currentDeviceTheme: DeviceTheme? = null,
)
