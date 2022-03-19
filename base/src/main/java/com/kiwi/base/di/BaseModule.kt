package com.kiwi.base.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kiwi.base.BuildConfig
import com.kiwi.base.CrashReportingTree
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import timber.log.Timber

@InstallIn(SingletonComponent::class)
@Module
object BaseModule {

    @Provides
    @Singleton
    fun provideFirebaseCrashlytics(): FirebaseCrashlytics = FirebaseCrashlytics.getInstance()

    @Provides
    @Singleton
    fun provideTimberTree(crashReportingTree: CrashReportingTree): Timber.Tree =
        if (BuildConfig.DEBUG) {
            Timber.DebugTree()
        } else {
            crashReportingTree
        }
}
