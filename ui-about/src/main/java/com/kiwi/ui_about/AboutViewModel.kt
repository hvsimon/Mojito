package com.kiwi.ui_about

import android.app.Application
import androidx.core.content.pm.PackageInfoCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiwi.data.entities.DeviceTheme
import com.kiwi.data.repositories.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class AboutViewModel @Inject constructor(
    application: Application,
    private val preferenceRepository: PreferenceRepository,
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

    init {
        viewModelScope.launch {
            preferenceRepository.deviceTheme.collect { deviceTheme ->
                _uiState.update {
                    it.copy(deviceTheme = deviceTheme)
                }
            }
        }

        viewModelScope.launch {
            preferenceRepository.dynamicColors.collect { enableDynamicColors ->
                _uiState.update {
                    it.copy(enableDynamicColors = enableDynamicColors)
                }
            }
        }
    }

    fun enableDynamicColors(enable: Boolean) {
        viewModelScope.launch {
            preferenceRepository.enableDynamicColors(enable)
        }
    }

    fun setDeviceTheme(deviceTheme: DeviceTheme) {
        viewModelScope.launch {
            preferenceRepository.setDeviceTheme(deviceTheme)
        }
    }
}
