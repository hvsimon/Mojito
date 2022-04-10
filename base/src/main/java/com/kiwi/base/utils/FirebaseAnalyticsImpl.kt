package com.kiwi.base.utils

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import javax.inject.Inject
import javax.inject.Provider

internal class FirebaseAnalyticsImpl @Inject constructor(
    private val firebaseAnalytics: Provider<FirebaseAnalytics>,
) : Analytics {

    override fun trackScreenView(
        route: String?,
        arguments: Any?,
    ) {
        try {
            firebaseAnalytics.get().logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                if (route != null) param("screen_route", route)

                // Expand out the rest of the parameters
                when {
                    arguments is Bundle -> {
                        for (key in arguments.keySet()) {
                            val value = arguments.get(key).toString()
                            // We don't want to include the route twice
                            if (value == route) continue

                            param("screen_arg_$key", value)
                        }
                    }
                    arguments != null -> param("screen_arg", arguments.toString())
                }
            }
        } catch (t: Throwable) {
            // Ignore, Firebase is optional so it might not be setup for this project
        }
    }
}
