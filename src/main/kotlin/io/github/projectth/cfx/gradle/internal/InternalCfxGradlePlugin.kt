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
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

abstract class InternalCfxGradlePlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        setProperty("kotlin.js.ir.output.granularity", "whole-program")

        plugins.apply(KotlinMultiplatformPluginWrapper::class)

        extensions.create(CfxProjectExtension::class, "cfx", InternalCfxProjectExtension::class)
    }
}
