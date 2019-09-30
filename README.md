# SharedPrefsUtils-ktx:
[![Build Status](https://travis-ci.org/GuilhE/SharedPrefsUtils-ktx.svg?branch=master)](https://travis-ci.org/GuilhE/SharedPrefsUtils-ktx)  [![codecov](https://codecov.io/gh/GuilhE/SharedPrefsUtils-ktx/branch/master/graph/badge.svg)](https://codecov.io/gh/GuilhE/SharedPrefsUtils-ktx)  [![Codacy Badge](https://api.codacy.com/project/badge/Grade/9f39a3f9825745738946f3c11a97c3ed)](https://www.codacy.com/app/GuilhE/SharedPrefsUtils-ktx?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=GuilhE/SharedPrefsUtils-ktx&amp;utm_campaign=Badge_Grade)  [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SharedPrefsUtils-ktx-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/6114)

Save and load objects from SharedPreferences in a faster and simpler way.  
This project is a "Kotlin extensions" version of [SharedPrefsUtils](https://github.com/GuilhE/SharedPrefsUtils)

#### Version 1.x
  - **September, 2019** - Kotlin version

## Getting started

The first step is to include SharedPrefsUtils-ktx into your project, for example, as a Gradle compile dependency:

```groovy
implementation 'com.github.guilhe:SharedPrefsUtils-ktx:${LATEST_VERSION}'
```
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.guilhe/SharedPrefsUtils-ktx/badge.svg)](https://search.maven.org/search?q=a:SharedPrefsUtils-ktx)  [ ![Download](https://api.bintray.com/packages/gdelgado/android/SharedPrefsUtils-ktx/images/download.svg) ](https://bintray.com/gdelgado/android/SharedPrefsUtils-ktx/_latestVersion)
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
        val list = ArrayList<MyObjectType>()
        list.add(MyObjectType("string", 1, true))
        prefs.put("key", list)

        assertEquals(list, prefs.get("key", object : TypeToken<List<MyObjectType>>() {}, ArrayList()))
        assertNotEquals(list, prefs.get("key", List::class, ArrayList<MyObjectType>()))
    }

    @Test
    fun getObjectWithType2() {
        val list = ArrayList<Int>()
        list.add(1)
        prefs.put("key", list)

        assertEquals(list, prefs.get("key", object : TypeToken<List<Int>>() {}, ArrayList()))
        assertNotEquals(list, prefs.get("key", List::class, ArrayList<Int>()))
    }

    @Parcelize data class MyObjectType(val fieldA: String, val fieldB: Int, val fieldC: Boolean) : Parcelable
```
Both tests will ran to completion.

Regarding `assertNotEquals(list, prefs.get("key", List::class, ArrayList<Int>()))` being true, I guess it's related with the fact that `public <T> T fromJson(JsonReader reader, Type typeOfT){}` method from `Gson.java` (line 886) is type unsafe\:
 _"Since Type is not parameterized by T, this method is type unsafe and should be used carefully"_.
 That's why I believe I'm getting `List<Double>` instead of `List<Integer>`.

Also:
```java
prefs.put(prefs, "key", 1)
prefs.get(prefs, "key", Boolean::class, false)
```

Will throw `JsonParseException`.

## Binaries

Additional binaries and dependency information for can be found at [https://search.maven.org](https://search.maven.org/search?q=a:SharedPrefsUtils-ktx).

<a href='https://bintray.com/gdelgado/android/SharedPrefsUtils-ktx?source=watch' alt='Get automatic notifications about new "SharedPrefsUtils-ktx" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_bw.png'></a>

## Dependencies

- [Gson](https://github.com/google/gson)
- [Timber](https://github.com/JakeWharton/timber)

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/GuilhE/SharedPrefsUtils-ktx/issues).
 
## LICENSE

Copyright (c) 2019-present GuilhE

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
