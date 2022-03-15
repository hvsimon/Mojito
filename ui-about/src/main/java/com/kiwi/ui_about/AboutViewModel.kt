package com.kiwi.ui_about

import android.app.Application
import androidx.core.content.pm.PackageInfoCompat
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
internal class AboutViewModel @Inject constructor(
    application: Application,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AboutUiState(
            versionName = application.packageManager
                .getPackageInfo(application.packageName, 0)
                .versionName,
            versionCode = application.packageManager
                .getPackageInfo(application.packageName, 0)
                .let { PackageInfoCompat.getLongVersionCode(it) }
        )
    )
    val uiState = _uiState.asStateFlow()
}
