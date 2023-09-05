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

import io.github.projectth.cfx.gradle.dsl.CfxTargetContainer
import io.github.projectth.cfx.gradle.dsl.targets.CfxBrowserTargetDsl
import io.github.projectth.cfx.gradle.dsl.targets.CfxNodeJsTargetDsl
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.attributes.Attribute
import org.gradle.kotlin.dsl.newInstance
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.JsModuleKind
import org.jetbrains.kotlin.gradle.dsl.KotlinJsOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBinaryMode
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl
import org.jetbrains.kotlin.gradle.targets.js.ir.JsIrBinary
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig
import javax.inject.Inject

internal open class InternalCfxTargetContainer @Inject constructor(
    private val project: Project,
) : CfxTargetContainer {
    private val kotlinExtension = project.kotlinExtension as KotlinMultiplatformExtension

    private val sharedLazyDelegate = lazy { createCfxNodeJsTarget(SHARED_NAME) }

    override val shared by sharedLazyDelegate

    val isSharedInitialized: Boolean
        get() = sharedLazyDelegate.isInitialized()

    private val serverLazyDelegate = lazy { createCfxNodeJsTarget(SERVER_NAME, true) }

    override val server by serverLazyDelegate

    val isServerInitialized: Boolean
        get() = serverLazyDelegate.isInitialized()

    private val clientLazyDelegated = lazy { createCfxNodeJsTarget(CLIENT_NAME) }

    override val client by clientLazyDelegated

    val isClientInitialized: Boolean
        get() = clientLazyDelegated.isInitialized()

    private val uiLazyDelegated = lazy { createCfxBrowserTarget() }

    override val ui by uiLazyDelegated

    val isUiInitialized: Boolean
        get() = uiLazyDelegated.isInitialized()

    private fun createCfxNodeJsTarget(name: String, sourceMap: Boolean = false) =
        createKotlinJsTarget(name, sourceMap) {
            binaries.library()
            nodejs()
        }.let { project.objects.newInstance<CfxNodeJsTargetDsl>(it) }

    private fun createCfxBrowserTarget(name: String = UI_NAME) = createKotlinJsTarget(name, false) {
        binaries.executable()
        browser {
            webpackTask(Action {
                mainOutputFileName.set("index.js")
                sourceMaps = mode == KotlinWebpackConfig.Mode.DEVELOPMENT
                cssSupport {
                    enabled.set(true)
                }
            })
        }
    }.let { project.objects.newInstance<CfxBrowserTargetDsl>(it) }

    private fun createKotlinJsTarget(name: String, sourceMap: Boolean, fn: KotlinJsTargetDsl.() -> Unit) =
        kotlinExtension.js(name) {
            moduleName = name
            configureCommonOptions(sourceMap)
            fn()
        }

    private fun KotlinJsTargetDsl.configureCommonOptions(sourceMap: Boolean) {
        attributes.attribute(CFX_ATTRIBUTE, targetName)
        compilations.all {
            kotlinOptions.configureCommonJsOptions(sourceMap)

            binaries.whenObjectAdded {
                if (this !is JsIrBinary) return@whenObjectAdded

                linkTask.configure {
                    kotlinOptions.configureCommonJsOptions(this@whenObjectAdded.mode == KotlinJsBinaryMode.DEVELOPMENT || sourceMap)
                }
            }
        }
    }

    private fun KotlinJsOptions.configureCommonJsOptions(sourceMap: Boolean) {
        moduleKind = JsModuleKind.MODULE_COMMONJS.kind
        this.sourceMap = sourceMap
        if (sourceMap) {
            sourceMapEmbedSources = "never"
        }
    }

    companion object {
        const val SHARED_NAME = "shared"
        const val SERVER_NAME = "server"
        const val CLIENT_NAME = "client"
        const val UI_NAME = "ui"

        private val CFX_ATTRIBUTE = Attribute.of("io.github.projecth.cfx", String::class.java)
    }
}
