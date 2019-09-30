package com.github.guilhe.sharedprefsutils.ktx

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import timber.log.Timber
import kotlin.reflect.KClass

@Suppress("unused")
@Throws(IllegalArgumentException::class)
fun <T> SharedPreferences.put(key: String, value: T) {
    putObject(this, key, value, Gson())
}

@Suppress("unused")
@Throws(IllegalArgumentException::class)
fun <T> SharedPreferences.put(key: String, value: T, gson: Gson) {
    putObject(this, key, value, gson)
}

@Suppress("unused")
@Throws(JsonParseException::class)
fun <T> SharedPreferences.get(key: String, type: TypeToken<T>, default: T): T {
    return getObject(this, key, type, default, Gson())
}

@Suppress("unused")
@Throws(JsonParseException::class)
fun <T> SharedPreferences.get(key: String, type: TypeToken<T>, default: T, gson: Gson): T {
    return getObject(this, key, type, default, gson)
}

@Suppress("unused")
@Throws(JsonParseException::class)
fun <T : Any> SharedPreferences.get(key: String, clazz: KClass<T>, default: T): T {
    return getObject(this, key, clazz.javaObjectType, default, Gson())
}

@Suppress("unused")
@Throws(JsonParseException::class)
fun <T : Any> SharedPreferences.get(key: String, klass: KClass<T>, default: T, gson: Gson): T {
    return getObject(this, key, klass.javaObjectType, default, gson)
}

private fun <T> putObject(prefs: SharedPreferences, key: String, value: T, gson: Gson): Boolean {
    require(key.isNotEmpty()) { "Key must not be empty" }
    Timber.i("> putObject, storing ${value.toString()} with key $key")
    return prefs.edit().putString(key, gson.toJson(value)).commit()
}

@Throws(JsonParseException::class)
private fun <T> getObject(prefs: SharedPreferences, key: String, type: TypeToken<T>, default: T, gson: Gson): T {
    if (key.isEmpty()) {
        Timber.w("> getObject, key must not be empty")
        return default
    }
    val json = prefs.getString(key, null)
    return if (json == null) {
        Timber.i("> getObject, json is null, returning defaultValue")
        default
    } else {
        try {
            gson.fromJson(json, type.type) as T
        } catch (e: JsonSyntaxException) {
            throw JsonParseException("> getObject, Object stored with Key $key is instance of other class.")
        }
    }
}

@Throws(JsonParseException::class)
private fun <T> getObject(prefs: SharedPreferences, key: String, type: Class<T>, default: T, gson: Gson): T {
    if (key.isEmpty()) {
        Timber.w("> getObject, key must not be empty")
        return default
    }
    val json = prefs.getString(key, null)
    return if (json == null) {
        Timber.i("> getObject, json is null, returning defaultValue")
        default
    } else {
        try {
            gson.fromJson(json, type)
        } catch (e: JsonSyntaxException) {
            throw JsonParseException("> getObject, Object stored with Key $key is instance of other class.")
        }
    }
}