package com.kiwi.translate

@DslMarker
annotation class TranslationOptionsDsl

@TranslationOptionsDsl
class TranslationOptionsBuilder {

    var from: String? = null

    var to: List<String> = listOf()

    fun build(): TranslationOptions {
        to.ifEmpty { error("You must set at least one language for to") }

        return TranslationOptions(
            from = from,
            to = to,
        )
    }
}

data class TranslationOptions(
    val from: String?,
    val to: List<String>
)
