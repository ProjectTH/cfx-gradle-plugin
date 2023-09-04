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

import io.github.projectth.cfx.gradle.dsl.CfxSourceSetContainer
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

internal open class InternalCfxSourceSetContainer(sourceSets: NamedDomainObjectContainer<KotlinSourceSet>) :
    CfxSourceSetContainer {
    override val sharedMain: KotlinSourceSet by lazy { sourceSets[SHARED_MAIN_NAME] }
    override val sharedTest: KotlinSourceSet by lazy { sourceSets[SHARED_TEST_NAME] }

    override val serverMain: KotlinSourceSet by lazy { sourceSets[SERVER_MAIN_NAME] }
    override val serverTest: KotlinSourceSet by lazy { sourceSets[SERVER_TEST_NAME] }

    override val clientMain: KotlinSourceSet by lazy { sourceSets[CLIENT_MAIN_NAME] }
    override val clientTest: KotlinSourceSet by lazy { sourceSets[CLIENT_TEST_NAME] }

    override val uiMain: KotlinSourceSet by lazy { sourceSets[UI_MAIN_NAME] }
    override val uiTest: KotlinSourceSet by lazy { sourceSets[UI_TEST_NAME] }

    companion object {
        const val SHARED_MAIN_NAME = "sharedMain"
        const val SHARED_TEST_NAME = "sharedTest"

        const val SERVER_MAIN_NAME = "serverMain"
        const val SERVER_TEST_NAME = "serverTest"

        const val CLIENT_MAIN_NAME = "clientMain"
        const val CLIENT_TEST_NAME = "clientTest"

        const val UI_MAIN_NAME = "uiMain"
        const val UI_TEST_NAME = "uiTest"
    }
}
