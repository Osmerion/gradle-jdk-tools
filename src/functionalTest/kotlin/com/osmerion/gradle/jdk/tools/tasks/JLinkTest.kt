/*
 * Copyright 2024 Leon Linhart
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
package com.osmerion.gradle.jdk.tools.tasks

import com.osmerion.gradle.jdk.tools.AbstractFunctionalPluginTest
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path
import kotlin.io.path.writeText

/**
 * Functional tests for the `JLink` task.
 *
 * @author  Leon Linhart
 */
class JLinkTest : AbstractFunctionalPluginTest() {

    @field:TempDir
    lateinit var projectDir: Path

    private val buildFile: Path get() = projectDir.resolve("build.gradle.kts")
    private val settingsFile: Path get() = projectDir.resolve("settings.gradle.kts")

    @ParameterizedTest
    @MethodSource("provideGradleVersions")
    fun testJLink(gradleVersion: CharSequence) {
        writeSettingsFile()

        buildFile.writeText(
            """
            import com.osmerion.gradle.jdk.tools.tasks.*
            
            plugins {
                `java-library`
                id("com.osmerion.jdk-tools")
            }
            
            java {
                toolchain {
                    languageVersion.set(JavaLanguageVersion.of(17))
                }
            }
            
            tasks.register<JLink>("jlink") {
                destinationDirectory.set(layout.buildDirectory.dir("jlink-image"))
                addModules.add("java.base")
            }
            """.trimIndent()
        )

        val result = GradleRunner.create()
            .withGradleVersion(gradleVersion.toString())
            .withPluginClasspath()
            .withProjectDir(projectDir.toFile())
            .withArguments("jlink", "--info")
            .forwardOutput()
            .build()

        assertEquals(TaskOutcome.SUCCESS, result.task(":jlink")?.outcome)
    }

    private fun writeSettingsFile() {
        settingsFile.writeText(
            """
            pluginManagement {
                plugins {
                    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
                }
            }
            
            plugins {
                id("org.gradle.toolchains.foojay-resolver-convention")
            }
            """.trimIndent()
        )
    }

}