# JDK Tools Gradle Plugin

[![License](https://img.shields.io/badge/license-Apache%202.0-yellowgreen.svg?style=for-the-badge&label=License)](https://github.com/Osmerion/gradle-jdk-tools/blob/master/LICENSE)
[![Maven Central](https://img.shields.io/maven-central/v/com.osmerion.gradle.jdk.tools/gradle-jdk-tools.svg?style=for-the-badge&label=Maven%20Central)](https://maven-badges.herokuapp.com/maven-central/com.osmerion.gradle.jdk.tools/gradle-jdk-tools)
[![Gradle Plugin Portal](https://img.shields.io/maven-metadata/v.svg?style=for-the-badge&&label=Gradle%20Plugin%20Portal&logo=Gradle&metadataUrl=https%3A%2F%2Fplugins.gradle.org%2Fm2%2Fcom%2Fosmerion%2Fjdk%2Ftools%2Fcom.osmerion.jdk.tools.gradle.plugin%2Fmaven-metadata.xml)](https://plugins.gradle.org/plugin/com.osmerion.jdk-tools)
![Gradle](https://img.shields.io/badge/Gradle-8.0-green.svg?style=for-the-badge&color=1ba8cb&logo=Gradle)
![Java](https://img.shields.io/badge/Java-17-green.svg?style=for-the-badge&color=b07219&logo=Java)

This plugin is a library of tasks for working with the JDK tools that did not
make it into Gradle itself yet. As such, it provides the foundational building
blocks for working with `jlink`, `jpackage`, and more.

The plugin does not register and tasks or extensions, or modifies the project in
any way. If you are looking for an out-of-the-box solution, this is not the
right plugin for you.


## Compatibility

| JDK Tools Gradle Plugin | Supported Gradle versions |
|-------------------------|---------------------------|
| 0.1.0                   | 8.0+                      |


## Building from source

### Setup

This project uses [Gradle's toolchain support](https://docs.gradle.org/current/userguide/toolchains.html)
to detect and select the JDKs required to run the build. Please refer to the
build scripts to find out which toolchains are requested.

An installed JDK 1.8 (or later) is required to use Gradle.

### Building

Once the setup is complete, invoke the respective Gradle tasks using the
following command on Unix/macOS:

    ./gradlew <tasks>

or the following command on Windows:

    gradlew <tasks>

Important Gradle tasks to remember are:
- `clean`                   - clean build results
- `build`                   - assemble and test the Java library
- `publishToMavenLocal`     - build and install all public artifacts to the
                              local maven repository

Additionally `tasks` may be used to print a list of all available tasks.


## License

```
Copyright 2024 Leon Linhart

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```