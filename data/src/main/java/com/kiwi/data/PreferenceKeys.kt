package com.kiwi.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    val KEY_DEVICE_THEME = stringPreferencesKey("device_theme")
    val KEY_DYNAMIC_COLORS = booleanPreferencesKey("dynamic_colors")
}
