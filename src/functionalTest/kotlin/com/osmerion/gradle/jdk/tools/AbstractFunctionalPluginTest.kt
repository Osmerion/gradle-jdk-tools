/*
 * Copyright (c) 2022-2024 Leon Linhart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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