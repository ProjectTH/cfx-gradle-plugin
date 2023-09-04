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

package io.github.projectth.cfx.gradle.internal.dsl

import io.github.projectth.cfx.gradle.dsl.CfxProjectExtension
import io.github.projectth.cfx.gradle.dsl.CfxSourceSetContainer
import io.github.projectth.cfx.gradle.dsl.CfxTargetContainer
import org.gradle.api.Project
import org.gradle.api.model.ObjectFactory
import org.gradle.kotlin.dsl.newInstance
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import javax.inject.Inject

internal abstract class InternalCfxProjectExtension @Inject constructor(
    project: Project, objects: ObjectFactory,
) : CfxProjectExtension {
    private val kotlinExtension = project.kotlinExtension as KotlinMultiplatformExtension

    override val targets: CfxTargetContainer
        get() = TODO("Not yet implemented")

    override val sourceSets: CfxSourceSetContainer =
        objects.newInstance<InternalCfxSourceSetContainer>(kotlinExtension.sourceSets)
}
