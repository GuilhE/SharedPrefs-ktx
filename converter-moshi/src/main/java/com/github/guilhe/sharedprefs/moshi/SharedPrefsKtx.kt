package com.github.guilhe.sharedprefs.moshi

import android.content.SharedPreferences
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.rawType
import timber.log.Timber
import java.lang.reflect.Type
import kotlin.reflect.KClass

fun <T : Any> SharedPreferences.put(key: String, value: T, moshi: Moshi = Moshi.Builder().build()): Boolean = put(key, value, value.javaClass, moshi)

fun <T : Any> SharedPreferences.put(key: String, value: T, type: Type, moshi: Moshi = Moshi.Builder().build()): Boolean {
    require(key.isNotEmpty()) { "Key must not be empty" }
    val json = moshi.adapter<T>(type).toJson(value)
    Timber.i("> putObject, storing $json with key $key")
    return edit().putString(key, json).commit()
}

fun <T : Any> SharedPreferences.get(key: String, klass: KClass<T>, default: T, moshi: Moshi = Moshi.Builder().build()): T =
    getObject(this, key, klass.javaObjectType.rawType, default, moshi)

fun <T : Any> SharedPreferences.get(key: String, type: Type, default: T, moshi: Moshi = Moshi.Builder().build()): T =
    getObject(this, key, type, default, moshi)

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
            Timber.e("> getObject, Object stored with Key $key not able to instance to $type")
            throw e
        }
    }
}