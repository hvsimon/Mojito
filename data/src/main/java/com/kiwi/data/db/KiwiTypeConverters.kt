package com.kiwi.data.db

import androidx.room.TypeConverter
import com.kiwi.data.entities.Ingredient
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

object KiwiTypeConverters {

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return Json.encodeToString(ListSerializer(String.serializer()), value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return Json.decodeFromString(ListSerializer(String.serializer()), value)
    }

    @TypeConverter
    fun fromStringSet(value: Set<String>): String {
        return Json.encodeToString(SetSerializer(String.serializer()), value)
    }

    @TypeConverter
    fun toStringSet(value: String): Set<String> {
        return Json.decodeFromString(SetSerializer(String.serializer()), value)
    }

    @TypeConverter
    fun fromIngredientList(value: List<Ingredient>): String {
        return Json.encodeToString(ListSerializer(Ingredient.serializer()), value)
    }

    @TypeConverter
    fun toIngredientList(value: String): List<Ingredient> {
        return Json.decodeFromString(ListSerializer(Ingredient.serializer()), value)
    }

}