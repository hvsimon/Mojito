package com.kiwi.data

import com.kiwi.data.entities.BaseLiquor
import com.kiwi.data.entities.IBACocktail
import java.nio.charset.Charset
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSource

object DataJsonParser {

    @Throws(SerializationException::class)
    fun parseBaseLiquorData(source: BufferedSource): List<BaseLiquor> {
        return source.readString(Charset.defaultCharset())
            .let { Json.decodeFromString(ListSerializer(BaseLiquor.serializer()), it) }
    }

    @Throws(SerializationException::class)
    fun parseIBACocktailData(source: BufferedSource): List<IBACocktail> {
        return source
            .readString(Charset.defaultCharset())
            .let { Json.decodeFromString(ListSerializer(IBACocktail.serializer()), it) }
    }
}