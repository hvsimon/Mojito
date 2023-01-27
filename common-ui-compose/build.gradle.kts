plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    namespace = "com.kiwi.common_ui_compose"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":common-ui-resources"))

    // Androidx
    implementation(libs.lifecycle.runtime)
    implementation(libs.navigation.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
}
