# SharedPrefs-ktx
[![codecov](https://codecov.io/gh/GuilhE/SharedPrefs-ktx/branch/master/graph/badge.svg)](https://codecov.io/gh/GuilhE/SharedPrefs-ktx) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-SharedPrefs--ktx-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7905)

Save and load objects from SharedPreferences in a faster and simpler way.  
This project is a "Kotlin extensions" version of [SharedPrefsUtils](https://github.com/GuilhE/SharedPrefsUtils)

## Installation

To use with gson:
```groovy
implementation 'com.github.guilhe.sharedprefs-ktx:converter-gson:${LATEST_VERSION}'
```
[![Maven Central](https://img.shields.io/maven-central/v/com.github.guilhe.sharedprefs-ktx/converter-gson)](https://search.maven.org/search?q=g:com.github.guilhe.sharedprefs-ktx%20AND%20a:converter-gson)

To use with moshi:
```groovy
implementation 'com.github.guilhe.sharedprefs-ktx:converter-moshi:${LATEST_VERSION}'
```
[![Maven Central](https://img.shields.io/maven-central/v/com.github.guilhe.sharedprefs-ktx/converter-moshi)](https://search.maven.org/search?q=g:com.github.guilhe.sharedprefs-ktx%20AND%20a:converter-moshi)

## Usage

To use it with Gson check [here](converter-gson)  
To use it with Moshi check [here](converter-moshi)

## Dependencies

- [Gson](https://github.com/google/gson)
- [Moshi](https://github.com/square/moshi)
- [Timber](https://github.com/JakeWharton/timber)

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues]( https://github.com/GuilhE/SharedPrefs-ktx/issues).
 
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
