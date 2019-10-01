package com.github.guilhe.sharedprefs.moshi

import android.content.SharedPreferences
import com.squareup.moshi.*
import timber.log.Timber
import java.lang.reflect.Type
import kotlin.reflect.KClass

fun <T : Any> SharedPreferences.put(key: String, value: T, klass: KClass<T>, moshi: Moshi = Moshi.Builder().build()) {
    putObject(this, key, value, klass.javaObjectType, moshi)
}

fun <T : Any> SharedPreferences.put(key: String, value: T, type: Type, moshi: Moshi = Moshi.Builder().build()) {
    putObject(this, key, value, type, moshi)
}

private fun <T : Any> putObject(prefs: SharedPreferences, key: String, value: T, type: Type, moshi: Moshi): Boolean {
    require(key.isNotEmpty()) { "Key must not be empty" }

    val json = moshi.adapter<T>(type).toJson(value)
    Timber.i("> putObject, storing $json with key $key")

    return prefs.edit().putString(key, json).commit()
}

fun <T : Any> SharedPreferences.get(key: String, type: Type, default: T, moshi: Moshi = Moshi.Builder().build()): T {
    return getObject(this, key, type, default, moshi)
}

fun <T : Any> SharedPreferences.get(key: String, klass: KClass<T>, default: T, moshi: Moshi = Moshi.Builder().build()): T {
    return getObject(this, key, klass.javaObjectType, default, moshi)
}

private fun <T : Any> getObject(prefs: SharedPreferences, key: String, type: Type, default: T, moshi: Moshi): T {
    if (key.isEmpty()) {
        Timber.w("> getObject, key must not be empty, returning defaultValue")
        return default
    }
    val json = prefs.getString(key, null)
    return if (json == null) {
        Timber.w("> getObject, json is null, returning defaultValue")
        default
    } else {
        try {
            moshi.adapter<T>(type).fromJson(json)!!
        } catch (e: JsonDataException) {
            throw ClassCastException("> getObject, Object stored with Key $key is instance of other class. (not $type)")
        }
    }
}

private fun <T : Any> getObject(prefs: SharedPreferences, key: String, clazz: Class<T>, default: T, moshi: Moshi): T {
    if (key.isEmpty()) {
        Timber.w("> getObject, key must not be empty")
        return default
    }
    val json = prefs.getString(key, null)
    return if (json == null) {
        Timber.w("> getObject, json is null, returning defaultValue")
        default
    } else {
        try {
            moshi.adapter<T>(clazz).fromJson(json)!!
        } catch (e: JsonDataException) {
            throw ClassCastException("> getObject, Object stored with Key $key is instance of other class. (not $clazz)")
        }
    }
}