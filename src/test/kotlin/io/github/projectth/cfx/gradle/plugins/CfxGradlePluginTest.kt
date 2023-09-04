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

package io.github.projectth.cfx.gradle.plugins

import io.github.projectth.cfx.gradle.buildProject
import io.github.projectth.cfx.gradle.internal.InternalCfxGradlePlugin
import io.github.projectth.cfx.gradle.internal.dsl.InternalCfxProjectExtension
import org.gradle.kotlin.dsl.hasPlugin
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class CfxGradlePluginTest {
    @Test
    fun `plugins should be applied`() {
        buildProject {
            assertTrue(plugins.hasPlugin(CfxGradlePlugin::class))
            assertTrue(plugins.hasPlugin(InternalCfxGradlePlugin::class))
            assertTrue(plugins.hasPlugin("org.jetbrains.kotlin.multiplatform"))
        }
    }

    @Test
    fun `cfx extension should be created`() {
        buildProject {
            val extension = extensions.findByName("cfx")
            assertNotNull(extension)
            assertTrue(extension is InternalCfxProjectExtension)
        }
    }
}
