# Moshi Converter
A `Converter` which uses [Moshi][1] for serialization to and from JSON.

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
val list = mutableListOf(1)
val type = Types.newParameterizedType(List::class.java, Integer::class.java)

prefs.put("key", list, type)
prefs.get("key", type, mutableListOf<Int>()))
```

When __not__ using primitive types you should use `Types.newParameterizedType` instead of `T::class`, for example:
```java
    @Test
    fun getObjectWithType() {
        val list = mutableListOf(MyObjectType("string", 1, true))
        val type = Types.newParameterizedType(List::class.java, MyObjectType::class.java)
        
        prefs.put("key", list, type)
        assertEquals(list, prefs.get("key", type, mutableListOf<MyObjectType>()))
        assertNotEquals(list, prefs.get("key", List::class, mutableListOf<MyObjectType>()))
    }
    
    @Test
    fun getObjectWithType2() {
        val list = mutableListOf(1)
        val type = Types.newParameterizedType(List::class.java, Integer::class.java)
        
        prefs.put("key", list, type)
        assertEquals(list, prefs.get("key", type, mutableListOf<Int>()))
        assertNotEquals(list, prefs.get("key", List::class, mutableListOf<Int>()))
    }

    @Parcelize data class MyObjectType(val fieldA: String, val fieldB: Int, val fieldC: Boolean) : Parcelable
```
Both tests will ran to completion.

Regarding both `assertNotEquals()` being true, it's because we are providing a `KClass<T>` instead of a `Type`. That will delegate the action to the`StandardJsonAdapters.java` instead of `JsonAdapter.java`. Thus, in the second test, the `JsonAdapter<Double> DOUBLE_JSON_ADAPTER` (`case NUMBER:...`) will be chosen to complete the operation.  
That's why I we get `List<Double>` instead of `List<Integer>`.

Also:
```java
prefs.put("key", 1)
prefs.get("key", Boolean::class, false)
```

Will throw `JsonDataException`.

 [1]: https://github.com/square/moshi
