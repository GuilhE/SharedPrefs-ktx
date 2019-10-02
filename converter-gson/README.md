# Gson Converter
A `Converter` which uses [Gson][1] for serialization to and from JSON.

## Sample usage
Get a hold of `SharedPreferences` instance to use the extensions `put` and `get`:
```java
lateinit var prefs: SharedPreferences
...
prefs = appContext.getSharedPreferences("test", Context.MODE_PRIVATE)
```

To save and load primitive types:
```java
prefs.put("key", 1)
val a = prefs.get("key", Int::class, 1)
```

To save and load object types:
```java
val list = mutableListOf<Int>()
prefs.put("key", list)
list = prefs.get("key", object : TypeToken<List<Int>>() {}, mutableListOf()))
```

When __not__ using primitive types you should use `TypeToken` instead of `T::class`, for example:
```java
    @Test
    fun getObjectWithType() {
        val list = ArrayList<MyObjectType>().apply { this.add(MyObjectType("string", 1, true)) }
        
        prefs.put("key", list)
        assertEquals(list, prefs.get("key", object : TypeToken<List<MyObjectType>>() {}, ArrayList()))
        assertNotEquals(list, prefs.get("key", List::class, ArrayList<MyObjectType>()))
    }

    @Test
    fun getObjectWithType2() {
        val list = ArrayList<Int>().apply { this.add(1) }

        prefs.put("key", list)
        assertEquals(list, prefs.get("key", object : TypeToken<List<Int>>() {}, ArrayList()))
        assertNotEquals(list, prefs.get("key", List::class, ArrayList<Int>()))
    }

    @Parcelize data class MyObjectType(val fieldA: String, val fieldB: Int, val fieldC: Boolean) : Parcelable
```
Both tests will ran to completion.

Regarding `assertNotEquals(list, prefs.get("key", List::class, ArrayList<Int>()))` being true, it's related with the fact that `public <T> T fromJson(JsonReader reader, Type typeOfT){}` method from `Gson.java` (line 886) is type unsafe\:
 _"Since Type is not parameterized by T, this method is type unsafe and should be used carefully"_.
 That's why I believe I'm getting `List<Double>` instead of `List<Integer>`.

Also:
```java
prefs.put(prefs, "key", 1)
prefs.get(prefs, "key", Boolean::class, false)
```
Will throw `JsonParseException`.

## Binaries
Additional binaries and dependency information for can be found at [https://search.maven.org](https://search.maven.org/search?q=g:com.github.guilhe%20AND%20a:converter-gson).  
<a href='https://bintray.com/gdelgado/android/SharedPrefs-ktx%3Agson?source=watch' alt='Get automatic notifications about new "SharedPrefs-ktx:gson" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_bw.png'></a><a href='https://bintray.com/gdelgado/android/SharedPrefs-ktx%3Agson?source=watch' alt='Get automatic notifications about new "SharedPrefs-ktx:gson" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_bw.png'></a>

 [1]: https://github.com/google/gson