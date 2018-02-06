# Android-Debug-Tools
[![](https://jitpack.io/v/kk121/Android-Debug-Tools.svg)](https://jitpack.io/#kk121/Android-Debug-Tools)
<br>
### Android library for debugging, Sqlite Databases, SharedPreferences and all types of files stored in internal storage directory, right from the app.

## What it does?
* View all Sqlite database with version.
* View all Tables and it's contents in a Database.
* View all SharedPreferences.
* View all files(image, pdf, json, txt etc) stored in internal directory of the app.

## Download
### Gradle:
```sh
repositories {
    mavenCentral()
    maven { url 'https://jitpack.io' }
}
    
dependencies {
  //include in debug build only
  debugCompile 'com.github.kk121:Android-Debug-Tools:1.0'
}

```
### or Maven:
```sh
<dependency>
  <groupId>com.github.kk121</groupId>
  <artifactId>Android-Debug-Tools</artifactId>
  <version>1.0</version>
</dependency>
```

## Using Android-Debug-Tools in your Application?
```sh
  //start the ActivityDebugTools activity from your activity, that's it
  startActivity(new Intent(this, ActivityDevTools.class));
```

## Screenshots:
<img src="https://github.com/kk121/Android-Debug-Tools/blob/master/screenshots/drawer.png" width="350"> &nbsp; &nbsp; &nbsp; &nbsp; <img src="https://github.com/kk121/Android-Debug-Tools/blob/master/screenshots/database%20list.png" width="350">

### Tables
<img src="https://github.com/kk121/Android-Debug-Tools/blob/master/screenshots/table%20list.png" width="350"> &nbsp; &nbsp; &nbsp; &nbsp; <img src="https://github.com/kk121/Android-Debug-Tools/blob/master/screenshots/table%20content.png" width="350">

### SharedPreference
<img src="https://github.com/kk121/Android-Debug-Tools/blob/master/screenshots/sharedPreference.png" width="350">

## Upcoming Features:
* Edit, delete database tables
* Edit sharedPreferences
* Delete, add, or create files in internal storage directory

## Find this library useful ? :heart:
* Support it by clicking the :star:

## License
```
   Copyright (C) 2018 Krishna Kumar

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
