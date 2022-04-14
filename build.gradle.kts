import org.jlleitschuh.gradle.ktlint.KtlintExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.pluginGradle)
        classpath(libs.kotlin.pluginGradle)
        classpath(libs.hilt.pluginGradle)
        classpath(libs.aboutlibraries.pluginGradle)
        classpath(libs.googleservice.pluginGradle)
        classpath(libs.crashlytics.pluginGradle)
    }
}

plugins {
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<KtlintExtension> {
        android.set(true)
        outputColorName.set("RED")
    }
}
