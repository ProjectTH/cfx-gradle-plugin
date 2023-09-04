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

/**
 * DSL extension to configure Cfx resource development environment for the entire project.
 *
 * You also can configure Cfx resource development environment via `kotlin` extension but it is not recommended.
 */
interface CfxProjectExtension {
    /**
     * The DSL to configure Kotlin/JS targets for Cfx resource.
     *
     * By default, there is no target configured. You need to configure at least one target.
     */
    val targets: CfxTargetContainer

    /**
     * The DSL to configure Cfx target source sets.
     */
    val sourceSets: CfxSourceSetContainer

    /**
     * Configures the Cfx targets with given [fn].
     */
    fun targets(fn: Action<CfxTargetContainer>) = fn.execute(targets)

    /**
     * Configures the Cfx target source sets with given [fn].
     */
    fun sourceSets(fn: Action<CfxSourceSetContainer>) = fn.execute(sourceSets)
}
