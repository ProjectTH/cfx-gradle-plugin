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

package io.github.projectth.cfx.gradle.dsl.targets

import org.gradle.api.Action
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsNodeDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrTarget
import javax.inject.Inject

/**
 * DSL wrapper to configure Kotlin/JS target for Cfx browser environment (NUI).
 *
 * This class will disable Node.js target configuration.
 *
 * @see KotlinJsTargetDsl
 */
open class CfxBrowserTargetDsl @Inject constructor(delegate: KotlinJsIrTarget) : CfxKotlinJsTargetWrapper(delegate) {
    @Deprecated(UNSUPPORTED_NODE_JS_TARGET_MESSAGE, ReplaceWith(""), DeprecationLevel.ERROR)
    override fun nodejs() = Unit

    @Deprecated(UNSUPPORTED_NODE_JS_TARGET_MESSAGE, ReplaceWith(""), DeprecationLevel.ERROR)
    override fun nodejs(fn: Action<KotlinJsNodeDsl>) = Unit

    @Deprecated(UNSUPPORTED_NODE_JS_TARGET_MESSAGE, ReplaceWith(""), DeprecationLevel.ERROR)
    override fun nodejs(body: KotlinJsNodeDsl.() -> Unit) = Unit

    private companion object {
        const val UNSUPPORTED_NODE_JS_TARGET_MESSAGE =
            "Kotlin/JS Node.js target is not supported for CfxBrowserTargetDsl"
    }
}
