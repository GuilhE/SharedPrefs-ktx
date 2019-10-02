# SharedPrefs-ktx:
[![Build Status](https://travis-ci.org/GuilhE/SharedPrefs-ktx.svg?branch=master)](https://travis-ci.org/GuilhE/SharedPrefs-ktx)[ ![codecov](https://codecov.io/gh/GuilhE/SharedPrefs-ktx/branch/master/graph/badge.svg)](https://codecov.io/gh/GuilhE/SharedPrefs-ktx)[ ![Codacy Badge](https://api.codacy.com/project/badge/Grade/db0d3641099f4903b2524c67a7e5a5b0)](https://www.codacy.com/manual/GuilhE/SharedPrefs-ktx?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=GuilhE/SharedPrefs-ktx&amp;utm_campaign=Badge_Grade)

Save and load objects from SharedPreferences in a faster and simpler way.  
This project is a "Kotlin extensions" version of [SharedPrefsUtils](https://github.com/GuilhE/SharedPrefsUtils)

**Version 1.x**  
- **October, 2019** - Added multi-module
- **September, 2019** - Kotlin version

## Getting started

The first step is to include SharedPrefs-ktx into your project, for example, as a Gradle compile dependency:

```groovy
implementation 'com.github.guilhe.sharedprefs-ktx:converter-gson:${LATEST_VERSION}'
```
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.guilhe.sharedprefs-ktx/converter-gson/badge.svg)](https://search.maven.org/search?q=g:com.github.guilhe.sharedprefs-ktx%20AND%20a:converter-gson)[ ![Download](https://api.bintray.com/packages/gdelgado/android/SharedPrefs-ktx%3Agson/images/download.svg) ](https://bintray.com/gdelgado/android/SharedPrefs-ktx%3Agson/_latestVersion)
```groovy
implementation 'com.github.guilhe.sharedprefs-ktx:converter-moshi:${LATEST_VERSION}'
```
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.guilhe.sharedprefs-ktx/converter-moshi/badge.svg)](https://search.maven.org/search?q=g:com.github.guilhe.sharedprefs-ktx%20AND%20a:converter-moshi)[ ![Download](https://api.bintray.com/packages/gdelgado/android/SharedPrefs-ktx%3Amoshi/images/download.svg) ](https://bintray.com/gdelgado/android/SharedPrefs-ktx%3Amoshi/_latestVersion)

## Sample usage

To use it with Gson check [here](/converter-gson)  
To use it with Moshi check [here](/converter-moshi)

## Dependencies

- [Gson](https://github.com/google/gson)
- [Moshi](https://github.com/square/moshi)
- [Timber](https://github.com/JakeWharton/timber)

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/GuilhE/SharedPrefs-ktx/issues).
 
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
