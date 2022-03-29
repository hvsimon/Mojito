package com.kiwi.translate

import java.io.IOException
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

@OptIn(ExperimentalSerializationApi::class)
private val json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
}

class AzureTranslator(
    private val key: String = BuildConfig.AZURE_TRANSLATE_API_KEY,
    private val client: OkHttpClient = OkHttpClient(),
) {

    @Throws(IOException::class)
    fun translate(
        text: String,
        builder: TranslationOptionsBuilder.() -> Unit,
    ): List<AzureTranslationData> {

        val translationOptions = TranslationOptionsBuilder().apply(builder).build()

        val translateUrl = HttpUrl.Builder()
            .scheme("https")
            .host(BASE_HOST)
            .addPathSegment(TRANSLATE_PATH)
            .addQueryParameter("api-version", "3.0")
            .apply {
                translationOptions.from?.let { addQueryParameter("from", it) }
                translationOptions.to.forEach { addQueryParameter("to", it) }
            }
            .build()

        val mediaType = "application/json".toMediaTypeOrNull()
        val body = "[{\"Text\": \"$text\"}]".toRequestBody(mediaType)

        val request = Request.Builder()
            .header("Ocp-Apim-Subscription-Key", key)
            .url(translateUrl)
            .post(body)
            .build()

        return client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            json.decodeFromString(
                ListSerializer(AzureTranslationData.serializer()),
                response.body!!.string()
            )
        }
    }

    companion object {
        private const val BASE_HOST = "api.cognitive.microsofttranslator.com"
        private const val TRANSLATE_PATH = "translate"
    }
}
