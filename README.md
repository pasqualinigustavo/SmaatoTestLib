Welcome to SmattoTestLib!
===================


I created these sample project to show how easy is make an library and publish it on bintray.
This project show how to download and parse a json file without use any 3rd parties libs.

----------

Adding Dependency - Gradle
-------------------

In PROJECT build.gradle
allprojects {
   repositories {
      jcenter()
   }
}

In APP level build.gradle
repositories {
   mavenCentral()
   maven {
      url 'https://dl.bintray.com/pasqualinigustavo/smatootest/'
   }
}

<code> compile 'com.github.pasqualinigustavo:smattotestlib:0.0.1'</code>

----------

License
--------------------

> Copyright 2016 Gustavo Pasqualini
> 
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at <p>
http://www.apache.org/licenses/LICENSE-2.0 <p>
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
<p>
See the License for the specific language governing permissions and
limitations under the License.
