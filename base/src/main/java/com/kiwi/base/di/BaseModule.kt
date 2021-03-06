package com.kiwi.base.di

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import com.kiwi.base.BuildConfig
import com.kiwi.base.utils.Analytics
import com.kiwi.base.utils.CrashReportingTree
import com.kiwi.base.utils.FirebaseAnalyticsImpl
import com.kiwi.translate.AzureTranslator
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import timber.log.Timber

@InstallIn(SingletonComponent::class)
@Module
abstract class BaseModule {

    @Singleton
    @Binds
    internal abstract fun provideAnalytics(bind: FirebaseAnalyticsImpl): Analytics

    companion object {

        @Provides
        @Singleton
        fun provideFirebaseCrashlytics(): FirebaseCrashlytics = Firebase.crashlytics

        @Provides
        @Singleton
        fun provideFirebaseAnalytics(): FirebaseAnalytics = Firebase.analytics

        @Provides
        @Singleton
        fun provideTimberTree(crashReportingTree: CrashReportingTree): Timber.Tree =
            if (BuildConfig.DEBUG) {
                Timber.DebugTree()
            } else {
                crashReportingTree
            }

        @Provides
        @Singleton
        fun provideTranslator(): AzureTranslator = AzureTranslator()
    }
}
