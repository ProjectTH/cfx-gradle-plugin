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

import io.github.projectth.cfx.gradle.dsl.targets.CfxBrowserTargetDsl
import io.github.projectth.cfx.gradle.dsl.targets.CfxNodeJsTargetDsl
import org.gradle.api.Action

/**
 * DSL wrapper to configure Kotlin/JS target for Cfx resource.
 *
 * **See also:** [Scripting in JavaScript](https://docs.fivem.net/docs/scripting-manual/runtimes/javascript/)
 */
interface CfxTargetContainer {
    /**
     * The shared target for both Cfx server-side and client-side.
     */
    val shared: CfxNodeJsTargetDsl

    /**
     * The server-side target for Cfx resource.
     */
    val server: CfxNodeJsTargetDsl

    /**
     * The client-side target for Cfx resource.
     */
    val client: CfxNodeJsTargetDsl

    /**
     * The NUI target for Cfx client resource.
     *
     * **See also:** [User interfaces with NUI](https://docs.fivem.net/docs/scripting-manual/nui-development/)
     */
    val ui: CfxBrowserTargetDsl

    /**
     * Configures the Cfx shared target with default options.
     */
    fun shared() = shared {}

    /**
     * Configures the Cfx shared target with default options and additional options specified by [fn].
     */
    fun shared(fn: Action<CfxNodeJsTargetDsl>) = fn.execute(shared)

    /**
     * Configures the Cfx server-side target with default options.
     */
    fun server() = server {}

    /**
     * Configures the Cfx server-side target with default options and additional options specified by [fn].
     */
    fun server(fn: Action<CfxNodeJsTargetDsl>) = fn.execute(server)

    /**
     * Configures the Cfx client-side target with default options.
     */
    fun client() = client {}

    /**
     * Configures the Cfx client-side target with default options and additional options specified by [fn].
     */
    fun client(fn: Action<CfxNodeJsTargetDsl>) = fn.execute(client)

    /**
     * Configures the Cfx NUI target with default options.
     */
    fun ui() = ui {}

    /**
     * Configures the Cfx NUI target with default options and additional options specified by [fn].
     */
    fun ui(fn: Action<CfxBrowserTargetDsl>) = fn.execute(ui)
}
