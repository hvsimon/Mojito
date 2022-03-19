package com.kiwi.cocktail

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var timberTree: Timber.Tree

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initDynamicColors()
    }

    private fun initTimber() {
        Timber.plant(timberTree)
    }

    private fun initDynamicColors() {
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
