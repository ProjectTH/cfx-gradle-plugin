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
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrTarget
import javax.inject.Inject

internal open class InternalCfxTargetContainer @Inject constructor(
    kotlinExtension: KotlinMultiplatformExtension,
) : CfxTargetContainer {
    private val sharedLazyDelegate = lazy {
        CfxNodeJsTargetDsl(kotlinExtension.js(SHARED_NAME) as KotlinJsIrTarget)
    }

    override val shared by sharedLazyDelegate

    val isSharedInitialized: Boolean
        get() = sharedLazyDelegate.isInitialized()

    private val serverLazyDelegate = lazy {
        CfxNodeJsTargetDsl(kotlinExtension.js(SERVER_NAME) as KotlinJsIrTarget)
    }

    override val server by serverLazyDelegate

    val isServerInitialized: Boolean
        get() = serverLazyDelegate.isInitialized()

    private val clientLazyDelegated = lazy {
        CfxNodeJsTargetDsl(kotlinExtension.js(CLIENT_NAME) as KotlinJsIrTarget)
    }

    override val client by clientLazyDelegated

    val isClientInitialized: Boolean
        get() = clientLazyDelegated.isInitialized()

    private val uiLazyDelegated = lazy {
        CfxBrowserTargetDsl(kotlinExtension.js(UI_NAME) as KotlinJsIrTarget)
    }

    override val ui by uiLazyDelegated

    val isUiInitialized: Boolean
        get() = uiLazyDelegated.isInitialized()

    companion object {
        const val SHARED_NAME = "shared"
        const val SERVER_NAME = "server"
        const val CLIENT_NAME = "client"
        const val UI_NAME = "ui"
    }
}
