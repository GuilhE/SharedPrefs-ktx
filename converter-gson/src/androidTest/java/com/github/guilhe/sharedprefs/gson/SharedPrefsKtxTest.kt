package com.github.guilhe.sharedprefs.gson

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
@SmallTest
class SharedPrefsKtxTest {

    private lateinit var prefs: SharedPreferences

    @get:Rule
    val exception: ExpectedException = ExpectedException.none()

    @Before
    fun init() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        prefs = appContext.getSharedPreferences("test", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    @Test
    fun putObjectWithNullValue() {
        prefs.put("key", null)
        assertEquals("null", prefs.getString("key", null))
    }

    @Test
    fun putObjectWithClass() {
        prefs.put("key", 1, Gson())

        var a = 1
        var b = prefs.get("key", Int::class, 1)
        assertEquals(a, b)

        a = 2
        b = prefs.get("KEY", Int::class, 2, Gson())
        assertEquals(a, b)
    }

    @Test
    fun putObjectWithType() {
        prefs.put("key", 1)

        val type = object : TypeToken<Int>() {}
        var b = prefs.get("key", type, 0)
        assertEquals(1, b)

        b = prefs.get("KEY", type, 2)
        assertEquals(2, b)

        b = prefs.get("KEY", type, 2)
        assertNotEquals(1, b.toLong())
    }

    @Test(expected = IllegalArgumentException::class)
    fun putObjectException1() {
        prefs.put("", Int::class)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun putObjectException2() {
        prefs.put("key", Int::class)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun putObjectException3() {
        prefs.put("key", Int::class)
    }

    @Test
    fun getObjectWithEmptyKey() {
        assertEquals(2, prefs.get("", Int::class, 2))
        assertEquals(2, prefs.get("", object : TypeToken<Int>() {}, 2, Gson()))
    }

    @Test
    fun getObjectWithClass() {
        val a = 1
        prefs.put("key", a)
        val b = prefs.get("key", Int::class, 2)
        assertEquals(a, b)
    }

    @Test
    fun getObjectWithType() {
        val list = ArrayList<MyObjectType>()
        list.add(MyObjectType("string", 1, true))
        prefs.put("key", list)

        assertEquals(list, prefs.get("key", object : TypeToken<List<MyObjectType>>() {}, ArrayList()))
        assertNotEquals(list, prefs.get("key", List::class, ArrayList<MyObjectType>()))
    }

    @Test
    fun getObjectWithType2() {
        val list = mutableListOf<Int>()
        list.add(1)
        prefs.put("key", list)

        assertEquals(list, prefs.get("key", object : TypeToken<List<Int>>() {}, ArrayList()))
        assertNotEquals(list, prefs.get("key", List::class, mutableListOf<Int>()))
    }

    @Test(expected = JsonParseException::class)
    fun getObjectWithClassException() {
        prefs.put("key", 1)
        prefs.get("key", Boolean::class, false, Gson())
    }

    @Test
    fun getObjectWithTypeException() {
        val list = ArrayList<Int>()
        list.add(1)
        prefs.put("key", list)

        exception.expect(JsonParseException::class.java)
        prefs.get("key", object : TypeToken<Float>() {}, 1f)
    }

    @Test
    fun getObjectReturnDefault() {
        prefs.put("key", 1)
        val defaultVal = 2
        assertEquals(defaultVal, prefs.get("key1", Int::class, defaultVal))
        assertEquals(defaultVal, prefs.get("key1", object : TypeToken<Int>() {}, defaultVal))
    }

    @Parcelize
    private data class MyObjectType(val fieldA: String, val fieldB: Int, val fieldC: Boolean) : Parcelable
}