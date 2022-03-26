package com.kiwi.cocktail

import androidx.lifecycle.ViewModel
import com.kiwi.data.repositories.PreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    preferenceRepository: PreferenceRepository,
) : ViewModel() {

    val deviceTheme = preferenceRepository.deviceTheme
    val enableDynamicColors = preferenceRepository.dynamicColors
}
