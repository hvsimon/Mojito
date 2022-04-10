package com.kiwi.base.utils

interface Analytics {

    fun trackScreenView(
        route: String?,
        arguments: Any? = null,
    )
}
