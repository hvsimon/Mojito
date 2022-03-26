package com.kiwi.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.kiwi.data.PreferenceKeys
import com.kiwi.data.entities.DeviceTheme
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Reusable
class PreferenceRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val deviceTheme: Flow<DeviceTheme> = dataStore.data
        .map { prefs ->
            prefs[PreferenceKeys.KEY_DEVICE_THEME]
                ?.let { DeviceTheme.valueOf(it) }
                ?: DeviceTheme.SYSTEM
        }

    val dynamicColors: Flow<Boolean> = dataStore.data
        .map { prefs ->
            prefs[PreferenceKeys.KEY_DYNAMIC_COLORS] ?: false
        }

    suspend fun setDeviceTheme(deviceTheme: DeviceTheme) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.KEY_DEVICE_THEME] = deviceTheme.name
        }
    }

    suspend fun enableDynamicColors(enable: Boolean) {
        dataStore.edit { prefs ->
            prefs[PreferenceKeys.KEY_DYNAMIC_COLORS] = enable
        }
    }
}
