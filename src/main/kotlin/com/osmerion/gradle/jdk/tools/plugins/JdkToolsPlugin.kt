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
package com.osmerion.gradle.jdk.tools.plugins

import com.osmerion.gradle.jdk.tools.tasks.JLink
import com.osmerion.gradle.jdk.tools.tasks.JPackage
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.jvm.toolchain.JavaToolchainService

/**
 * The "entry-point" of the plugin.
 *
 * @since   0.1.0
 *
 * @author  Leon Linhart
 */
public class JdkToolsPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.withPlugin("java-base") {
            val java = target.extensions.getByType(JavaPluginExtension::class.java)
            val javaToolchains = target.extensions.getByType(JavaToolchainService::class.java)

            target.tasks.withType(JLink::class.java) {
                executable.convention(javaToolchains.compilerFor(java.toolchain).map {
                    val file = it.executablePath.asFile
                    file.resolveSibling(file.name.replaceBefore('.', "jlink")).absolutePath
                })
            }

            target.tasks.withType(JPackage::class.java) {
                executable.convention(javaToolchains.compilerFor(java.toolchain).map {
                    val file = it.executablePath.asFile
                    file.resolveSibling(file.name.replaceBefore('.', "jpackage")).absolutePath
                })
            }
        }
    }

}