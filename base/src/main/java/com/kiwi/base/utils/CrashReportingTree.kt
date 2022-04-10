package com.kiwi.base.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import javax.inject.Provider
import timber.log.Timber

class CrashReportingTree @Inject constructor(
    private val firebaseCrashlytics: Provider<FirebaseCrashlytics>,
) : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.INFO
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        try {
            firebaseCrashlytics.get().run {
                this.log(message)

                if (t != null) {
                    if (priority == Log.ERROR) {
                        this.recordException(t)
                    }
                }
            }
        } catch (t: Throwable) {
            // Ignore, Firebase is optional so it might not be setup for this project
        }
    }
}
