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

@JvmInline
value class GradleVersion(private val raw: String) : CharSequence by raw {

    operator fun compareTo(other: String): Int {
        val thisParts = raw.split('.').map(String::toInt)
        val otherParts = other.split('.').map(String::toInt)

        for (i in 0 until maxOf(thisParts.size, otherParts.size)) {
            val thisPart = thisParts.getOrNull(i) ?: 0
            val otherPart = otherParts.getOrNull(i) ?: 0

            if (thisPart < otherPart) return -1
            if (thisPart > otherPart) return 1
        }

        return thisParts.size.compareTo(otherParts.size)
    }

    override fun toString(): String = raw

}