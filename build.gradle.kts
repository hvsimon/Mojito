import org.jlleitschuh.gradle.ktlint.KtlintExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
        classpath("com.mikepenz.aboutlibraries.plugin:aboutlibraries-plugin:10.0.0")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
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
