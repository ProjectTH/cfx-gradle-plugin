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

gradlePlugin {
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
    processResources {
        filesMatching("versions.properties") {
            expand("kotlinWrappers" to libs.versions.kotlinWrappers.get())
        }
    }

    validatePlugins {
        enableStricterValidation = true
    }
}
