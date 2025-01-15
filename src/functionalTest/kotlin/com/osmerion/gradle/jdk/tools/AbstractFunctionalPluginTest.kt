/*
 * Copyright 2024-2025 Leon Linhart
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.osmerion.gradle.jdk.tools

import org.gradle.api.JavaVersion

abstract class AbstractFunctionalPluginTest {

    protected companion object {

        @JvmStatic
        fun provideGradleVersions(): List<GradleVersion> = buildList {
            // See https://docs.gradle.org/current/userguide/compatibility.html
            val javaVersion = JavaVersion.current()

            add("8.12")
            add("8.11.1")
            add("8.10.1")
            add("8.9")
            add("8.8")
            add("8.7")
            add("8.6")
            add("8.5")
            if (javaVersion.majorVersion.toInt() >= 21) return@buildList

            add("8.4")
            add("8.3")

            if (javaVersion >= JavaVersion.VERSION_20) return@buildList

            add("8.2.1")
            add("8.1.1")
            add("8.0.2")
        }.map(::GradleVersion)

    }

}