package com.github.guilhe.sharedprefs.gson

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import kotlin.reflect.KClass

@Throws(IllegalArgumentException::class)
fun <T : Any> SharedPreferences.put(key: String, value: T, gson: Gson = Gson()) = putObject(this, key, value, gson)

private fun <T : Any> putObject(prefs: SharedPreferences, key: String, value: T, gson: Gson): Boolean {
    require(key.isNotEmpty()) { "Key must not be empty" }
    Timber.i("> putObject, storing $value with key $key")
    return prefs.edit().putString(key, gson.toJson(value)).commit()
}

@Throws(JsonParseException::class)
fun <T : Any> SharedPreferences.get(key: String, type: TypeToken<T>, default: T, gson: Gson = Gson()): T = getObject(this, key, type, default, gson)

@Throws(JsonParseException::class)
fun <T : Any> SharedPreferences.get(key: String, klass: KClass<T>, default: T, gson: Gson = Gson()): T =
    getObject(this, key, klass.javaObjectType, default, gson)

private fun <T> getObject(prefs: SharedPreferences, key: String, type: TypeToken<T>, default: T, gson: Gson): T {
    if (key.isEmpty()) {
        Timber.w("> getObject, key must not be empty")
        return default
    }
    val json = prefs.getString(key, null)
    return if (json == null) {
        Timber.i("> getObject, json is null for Key $key, returning defaultValue")
        default
    } else {
        try {
            gson.fromJson(json, type.type) as T
        } catch (e: JsonSyntaxException) {
            Timber.e("> getObject, Object stored with Key $key not able to instance to $type")
            throw e
        }
    }
}

private fun <T : Any> getObject(prefs: SharedPreferences, key: String, type: Class<T>, default: T, gson: Gson): T {
    if (key.isEmpty()) {
        Timber.w("> getObject, key must not be empty")
        return default
    }
    val json = prefs.getString(key, null)
    return if (json == null) {
        Timber.i("> getObject, json is null for Key $key, returning defaultValue")
        default
    } else {
        try {
            gson.fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            Timber.e("> getObject, Object stored with Key $key not able to instance to $type")
            throw e
        }
    }
}
