package com.kiwi.base

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import javax.inject.Inject
import timber.log.Timber

class CrashReportingTree @Inject constructor(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return priority >= Log.INFO
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        firebaseCrashlytics.log(message)

        if (t != null) {
            if (priority == Log.ERROR) {
                firebaseCrashlytics.recordException(t)
            }
        }
    }
}
