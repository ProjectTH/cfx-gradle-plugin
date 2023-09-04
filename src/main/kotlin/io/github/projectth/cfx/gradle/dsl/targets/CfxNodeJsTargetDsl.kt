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
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBrowserDsl
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsTargetDsl
import org.jetbrains.kotlin.gradle.targets.js.ir.KotlinJsIrTarget
import javax.inject.Inject

/**
 * DSL wrapper to configure Kotlin/JS target for Cfx Node.js environment.
 *
 * This class will disable browser target configuration.
 *
 * @see KotlinJsTargetDsl
 */
open class CfxNodeJsTargetDsl @Inject constructor(delegate: KotlinJsIrTarget) : CfxKotlinJsTargetWrapper(delegate) {
    @Deprecated(
        UNSUPPORTED_BROWSER_TARGET_MESSAGE,
        ReplaceWith(""),
        DeprecationLevel.ERROR,
    )
    override fun browser() = throwUnsupportedBrowserTarget()

    @Deprecated(
        UNSUPPORTED_BROWSER_TARGET_MESSAGE,
        ReplaceWith(""),
        DeprecationLevel.ERROR,
    )
    override fun browser(fn: Action<KotlinJsBrowserDsl>) = throwUnsupportedBrowserTarget()

    @Deprecated(
        UNSUPPORTED_BROWSER_TARGET_MESSAGE,
        ReplaceWith(""),
        DeprecationLevel.ERROR,
    )
    override fun browser(body: KotlinJsBrowserDsl.() -> Unit) = throwUnsupportedBrowserTarget()

    private fun throwUnsupportedBrowserTarget(): Nothing =
        throw UnsupportedOperationException(UNSUPPORTED_BROWSER_TARGET_MESSAGE)

    private companion object {
        private const val UNSUPPORTED_BROWSER_TARGET_MESSAGE =
            "Kotlin/JS browser target is not supported for CfxNodeJsTargetDsl"
    }
}
