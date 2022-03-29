package com.kiwi.translate

import kotlinx.serialization.Serializable

@Serializable
data class AzureTranslationData(
    val detectedLanguage: AzureDetectedLanguage?,
    val translations: List<AzureTranslation>
)

@Serializable
data class AzureDetectedLanguage(
    val language: String,
    val score: Float,
)

@Serializable
data class AzureTranslation(
    val text: String,
    val to: String,
)

fun List<AzureTranslationData>.getTranslationByLang(language: String): AzureTranslation? {
    return this.firstOrNull()
        ?.translations
        ?.firstOrNull { transition -> transition.to == language }
}
