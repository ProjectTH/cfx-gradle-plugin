@file:Suppress("UnstableApiUsage")

fun Project.properties(propertyName: String): Provider<String> = providers.gradleProperty(propertyName)

plugins {
    `kotlin-dsl`
    `maven-publish`
    signing
    alias(libs.plugins.gradle.pluginPublish)
}

group = properties("group").get()
version = properties("version").get()
description = properties("description").get()

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useKotlinTest(libs.versions.kotlin)

            targets.all {
                testTask {
                    systemProperties["test.plugin.id"] = properties("plugin.id").get()
                }
            }
        }

        val functionalTest by registering(JvmTestSuite::class) {
            testType = TestSuiteType.FUNCTIONAL_TEST
            useKotlinTest(libs.versions.kotlin)

            dependencies {
                implementation(project())
            }

            targets.all {
                testTask {
                    shouldRunAfter(test)
                    systemProperties["test.plugin.id"] = properties("plugin.id").get()
                }
            }
        }
    }
}

gradlePlugin {
    testSourceSets.add(sourceSets["functionalTest"])
    website = properties("plugin.website")
    vcsUrl = properties("plugin.vcsUrl")
    val cfx by plugins.creating {
        id = properties("plugin.id").get()
        implementationClass = properties("plugin.implementationClass").get()
        displayName = properties("plugin.displayName").get()
        description = project.description
        tags = properties("plugin.tags").get().split(",").map { it.trim() }
    }
}

tasks {
    check {
        dependsOn(testing.suites.named("functionalTest"))
    }

    processResources {
        filesMatching("versions.properties") {
            expand("kotlinWrappers" to libs.versions.kotlinWrappers.get())
        }
    }

    validatePlugins {
        enableStricterValidation = true
    }
}
