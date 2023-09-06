/*
 * Copyright 2023 Project TH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.projectth.cfx.gradle.internal

import io.github.projectth.cfx.gradle.dsl.CfxProjectExtension
import io.github.projectth.cfx.gradle.internal.dsl.InternalCfxProjectExtension
import io.github.projectth.cfx.gradle.internal.dsl.InternalCfxTargetContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.Zip
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.support.uppercaseFirstChar
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.extraProperties
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.distsDirectory
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.targets
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBinaryMode
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrTarget

abstract class InternalCfxGradlePlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        extraProperties["kotlin.js.ir.output.granularity"] = "whole-program"

        plugins.apply(KotlinMultiplatformPluginWrapper::class)

        extensions.create(CfxProjectExtension::class, "cfx", InternalCfxProjectExtension::class)
        configureCfxResourceDistributionTasks()
    }

    private fun Project.configureCfxResourceDistributionTasks() {
        registerCfxResourceDistributionTask(KotlinJsBinaryMode.DEVELOPMENT)
        registerCfxResourceDistributionTask(KotlinJsBinaryMode.PRODUCTION)
    }

    private fun Project.registerCfxResourceDistributionTask(mode: KotlinJsBinaryMode): TaskProvider<Copy> {
        val taskName = "$CFX_RESOURCE${mode.pascalName}Distribution"

        return tasks.register<Copy>(taskName) {
            group = CFX_GROUP
            description = "Generates a complete Cfx resource that can be used in a FxServer. (${mode.name.lowercase()})"

            doFirst {
                getMainTargetLibraryPrepareTasks(mode).forEach {
                    from(it) {
                        exclude("package.json")
                        into(MAIN_DIRECTORY_NAME)
                    }
                }

                getUiTargetWebpackTask(mode)?.let {
                    from(it) {
                        into(UI_DIRECTORY_NAME)
                    }
                }

                into(distsDirectory)
            }
        }
    }

    private fun Project.getMainTargetLibraryPrepareTasks(mode: KotlinJsBinaryMode) =
        mainTargets.map { project.tasks.named("${it.moduleName}Node${mode.pascalName}LibraryPrepare") }

    private fun Project.getUiTargetWebpackTask(mode: KotlinJsBinaryMode) =
        uiTarget?.let { project.tasks.named("${it.moduleName}Browser${mode.pascalName}Webpack") }

    private val Project.mainTargets
        get() = kotlinExtension.targets.filterIsInstance<KotlinJsIrTarget>()
            .filter { it.targetName in MAIN_TARGET_NAMES }

    private val Project.uiTarget
        get() = kotlinExtension.targets.filterIsInstance<KotlinJsIrTarget>().find { it.targetName == UI_TARGET }

    private val KotlinJsBinaryMode.pascalName: String
        get() = name.lowercase().uppercaseFirstChar()

    companion object {
        private const val CFX_GROUP = "cfx"
        private const val CFX_RESOURCE = "cfxResource"
        private const val CFX_RESOURCE_DIST_ZIP_TASK_NAME = "${CFX_RESOURCE}DistZip"

        private const val MAIN_DIRECTORY_NAME = "main"
        private val MAIN_TARGET_NAMES = listOf(
            InternalCfxTargetContainer.SHARED_NAME,
            InternalCfxTargetContainer.SERVER_NAME,
            InternalCfxTargetContainer.CLIENT_NAME
        )

        private const val UI_DIRECTORY_NAME = "ui"
        private const val UI_TARGET = InternalCfxTargetContainer.UI_NAME
    }
}
