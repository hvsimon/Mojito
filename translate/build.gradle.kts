import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("plugin.serialization") version "1.6.10"
}

val azureTranslateApiKey: String =
    gradleLocalProperties(rootDir).getProperty("AZURE_TRANSLATE_API_KEY")

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        buildConfigField("String", "AZURE_TRANSLATE_API_KEY", azureTranslateApiKey)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.core)
}
