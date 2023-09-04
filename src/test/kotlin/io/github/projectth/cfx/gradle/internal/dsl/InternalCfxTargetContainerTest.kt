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

import io.github.projectth.cfx.gradle.buildProject
import io.github.projectth.cfx.gradle.dsl.CfxProjectExtension
import io.github.projectth.cfx.gradle.dsl.targets.CfxBrowserTargetDsl
import io.github.projectth.cfx.gradle.dsl.targets.CfxNodeJsTargetDsl
import io.github.projectth.cfx.gradle.withExtension
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJsCompilation
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.targets
import org.jetbrains.kotlin.gradle.targets.js.dsl.KotlinJsBinaryMode
import org.jetbrains.kotlin.gradle.targets.js.ir.JsIrBinary
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.junit.jupiter.api.assertDoesNotThrow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@Suppress("KotlinConstantConditions")
class InternalCfxTargetContainerTest {
    @Test
    fun `targets should be empty by default`() {
        buildProject {
            extensions.getByType<CfxProjectExtension>().apply {
                targets {
                    this as InternalCfxTargetContainer
                    assertFalse(isSharedInitialized)
                    assertFalse(isServerInitialized)
                    assertFalse(isClientInitialized)
                    assertFalse(isUiInitialized)
                }
            }

            assertEquals(1, kotlinExtension.targets.count())
            assertEquals("metadata", kotlinExtension.targets.first().targetName)
        }
    }

    @Test
    fun `shared target should be configured`() {
        buildProject {
            extensions.getByType<CfxProjectExtension>().apply {
                targets {
                    this as InternalCfxTargetContainer
                    shared {
                        this as CfxNodeJsTargetDsl
                        compilations.all {
                            this as KotlinJsCompilation
                            kotlinOptions.moduleKind = "umd"
                        }
                    }

                    assertTrue(isSharedInitialized)
                    assertNotNull(kotlinExtension.targets.find { it.targetName == "shared" })
                    kotlinExtension.targets.find { it.targetName == "shared" }?.let {
                        it.compilations.all {
                            this as KotlinJsCompilation
                            assertEquals("umd", kotlinOptions.moduleKind)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `server target should be configured`() {
        buildProject {
            extensions.getByType<CfxProjectExtension>().apply {
                targets {
                    this as InternalCfxTargetContainer
                    server {
                        this as CfxNodeJsTargetDsl
                        compilations.all {
                            this as KotlinJsCompilation
                            kotlinOptions.moduleKind = "umd"
                        }
                    }

                    assertTrue(isServerInitialized)
                    assertNotNull(kotlinExtension.targets.find { it.targetName == "server" })
                    kotlinExtension.targets.find { it.targetName == "server" }?.let {
                        it.compilations.all {
                            this as KotlinJsCompilation
                            assertEquals("umd", kotlinOptions.moduleKind)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `client target should be configured`() {
        buildProject {
            extensions.getByType<CfxProjectExtension>().apply {
                targets {
                    this as InternalCfxTargetContainer
                    client {
                        this as CfxNodeJsTargetDsl
                        compilations.all {
                            this as KotlinJsCompilation
                            kotlinOptions.moduleKind = "umd"
                        }
                    }

                    assertTrue(isClientInitialized)
                    assertNotNull(kotlinExtension.targets.find { it.targetName == "client" })
                    kotlinExtension.targets.find { it.targetName == "client" }?.let {
                        it.compilations.all {
                            this as KotlinJsCompilation
                            assertEquals("umd", kotlinOptions.moduleKind)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `ui target should be configured`() {
        buildProject {
            extensions.getByType<CfxProjectExtension>().apply {
                targets {
                    this as InternalCfxTargetContainer
                    ui {
                        this as CfxBrowserTargetDsl
                        compilations.all {
                            this as KotlinJsCompilation
                            kotlinOptions.moduleKind = "umd"
                        }
                    }

                    assertTrue(isUiInitialized)
                    assertNotNull(kotlinExtension.targets.find { it.targetName == "ui" })
                    kotlinExtension.targets.find { it.targetName == "ui" }?.let {
                        it.compilations.all {
                            this as KotlinJsCompilation
                            assertEquals("umd", kotlinOptions.moduleKind)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun `call DSL twice should not create a duplication target`() {
        buildProject {
            extensions.getByType<CfxProjectExtension>().apply {
                targets {
                    this as InternalCfxTargetContainer
                    assertDoesNotThrow {
                        shared()
                        shared()
                    }
                    assertEquals(1, kotlinExtension.targets.count { it.targetName == "shared" })

                    assertDoesNotThrow {
                        server()
                        server()
                    }
                    assertEquals(1, kotlinExtension.targets.count { it.targetName == "server" })

                    assertDoesNotThrow {
                        client()
                        client()
                    }
                    assertEquals(1, kotlinExtension.targets.count { it.targetName == "client" })

                    assertDoesNotThrow {
                        ui()
                        ui()
                    }
                    assertEquals(1, kotlinExtension.targets.count { it.targetName == "ui" })
                }
            }
        }
    }

    @Test
    fun `ui target webpack task should be configured`() {
        buildProject {
            extensions.getByType<CfxProjectExtension>().apply {
                targets {
                    this as InternalCfxTargetContainer
                    ui()
                }
            }

            assertEquals(
                "index.js",
                tasks.getByName<KotlinWebpack>("uiBrowserProductionWebpack").mainOutputFileName.get()
            )
        }
    }

    @Test
    fun `binaries should be configured`() {
        buildProject {
            extensions.getByType<CfxProjectExtension>().apply {
                targets {
                    this as InternalCfxTargetContainer
                    shared.binaries.withType<JsIrBinary>().forEach {
                        if (it.mode == KotlinJsBinaryMode.DEVELOPMENT) {
                            assertTrue(it.linkTask.get().kotlinOptions.sourceMap)
                        } else {
                            assertFalse(it.linkTask.get().kotlinOptions.sourceMap)
                        }
                    }
                }
            }
        }
    }
}
