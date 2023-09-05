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

package io.github.projectth.cfx.gradle.dsl

import org.gradle.api.Action
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

/**
 * DSL wrapper to configure Cfx target source sets.
 */
interface CfxSourceSetContainer {
    val sharedMain: KotlinSourceSet
    val sharedTest: KotlinSourceSet

    val serverMain: KotlinSourceSet
    val serverTest: KotlinSourceSet

    val clientMain: KotlinSourceSet
    val clientTest: KotlinSourceSet

    val uiMain: KotlinSourceSet
    val uiTest: KotlinSourceSet

    fun sharedMain(fn: Action<KotlinSourceSet>) = fn.execute(sharedMain)
    fun sharedTest(fn: Action<KotlinSourceSet>) = fn.execute(sharedTest)

    fun serverMain(fn: Action<KotlinSourceSet>) = fn.execute(serverMain)
    fun serverTest(fn: Action<KotlinSourceSet>) = fn.execute(serverTest)

    fun clientMain(fn: Action<KotlinSourceSet>) = fn.execute(clientMain)
    fun clientTest(fn: Action<KotlinSourceSet>) = fn.execute(clientTest)

    fun uiMain(fn: Action<KotlinSourceSet>) = fn.execute(uiMain)
    fun uiTest(fn: Action<KotlinSourceSet>) = fn.execute(uiTest)
}
