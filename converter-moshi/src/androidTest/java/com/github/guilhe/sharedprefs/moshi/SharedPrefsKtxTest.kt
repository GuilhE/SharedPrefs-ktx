package com.github.guilhe.sharedprefs.moshi

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.parcelize.Parcelize
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
    private lateinit var moshi: Moshi

    @get:Rule
    val exception: ExpectedException = ExpectedException.none()

    @Before
    fun init() {
        val appContext = ApplicationProvider.getApplicationContext<Context>()
        prefs = appContext.getSharedPreferences("test", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Test
    fun putObjectWithNullValue() {
        assertEquals(null, prefs.getString("key", null))
    }

    @Test
    fun putObjectWithClass() {
        prefs.put("key", 1, moshi)

        var a = 1
        var b = prefs.get("key", Int::class, 1)
        assertEquals(a, b)

        a = 2
        b = prefs.get("KEY", Int::class, 2, moshi)
        assertEquals(a, b)
    }

    @Test
    fun putObjectWithType() {
        prefs.put("key", 1)

        val type = Int::class
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
        prefs.put("key", Types.newParameterizedType(Int::class.java))
    }

    @Test
    fun getObjectWithEmptyKey() {
        assertEquals(2, prefs.get("", Int::class, 2))
        assertEquals(2, prefs.get("", Int::class.java, 2, moshi))
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
        val list = mutableListOf(MyObjectType("string", 1, true))
        val type = Types.newParameterizedType(List::class.java, MyObjectType::class.java)
        prefs.put("key", list, type, moshi)

        assertEquals(list, prefs.get("key", type, mutableListOf<MyObjectType>(), moshi))
        assertNotEquals(list, prefs.get("key", List::class, mutableListOf<MyObjectType>(), moshi))
    }

    @Test
    fun getObjectWithType2() {
        val list = mutableListOf(1)
        val type = Types.newParameterizedType(List::class.java, Integer::class.java)

        prefs.put("key", list, type)
        assertEquals(list, prefs.get("key", type, mutableListOf<Int>()))
        assertNotEquals(list, prefs.get("key", List::class, mutableListOf<Int>()))
    }

    @Test(expected = JsonDataException::class)
    fun getObjectWithClassException() {
        prefs.put("key", 1)
        prefs.get("key", Boolean::class, false, moshi)
    }

    @Test(expected = JsonDataException::class)
    fun getObjectWithTypeException() {
        val list = ArrayList<Int>().apply { this.add(1) }
        prefs.put("key", list, Types.newParameterizedType(List::class.java, Integer::class.java))
        prefs.get("key", MyObjectType::class.java, "123", moshi)
    }

    @Test(expected = JsonDataException::class)
    fun getObjectWithTypeException2() {
        prefs.put("key", "test", moshi)
        prefs.get("key", Float::class, 1f)
    }

    @Test
    fun getObjectReturnDefault() {
        prefs.put("key", 1)
        val defaultVal = 2
        assertEquals(defaultVal, prefs.get("key1", Int::class, defaultVal))
        assertEquals(defaultVal, prefs.get("", Int::class, defaultVal, moshi))
        assertEquals(defaultVal, prefs.get("key2", Int::class.java, defaultVal))
        exception.expect(IllegalArgumentException::class.java)
        assertEquals(defaultVal, prefs.get("key", Types.newParameterizedType(Int::class.java), defaultVal, moshi))
    }

    @Parcelize
    data class MyObjectType(val fieldA: String = "yay", val fieldB: Int = 0, val fieldC: Boolean = false) : Parcelable
}