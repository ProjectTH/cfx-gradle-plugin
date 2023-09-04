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

package io.github.projectth.cfx.gradle

import io.github.projectth.cfx.gradle.dsl.CfxProjectExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.testfixtures.ProjectBuilder

val TEST_PLUGIN_ID: String = System.getProperty("test.plugin.id")

fun buildProject(name: String = "test", plugin: String = TEST_PLUGIN_ID, action: Project.() -> Unit = {}) {
    ProjectBuilder.builder().withName(name).build().run {
        if (plugin.isNotEmpty()) plugins.apply(plugin)
        action()
    }
}

fun withExtension(fn: CfxProjectExtension.(project: Project) -> Unit) {
    buildProject {
        extensions.getByType<CfxProjectExtension>().fn(project)
    }
}
