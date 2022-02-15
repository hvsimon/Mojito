plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
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
    implementation(libs.lifecycle.runtime)

    // Compose
    implementation(libs.bundles.compose)
    // TODO: 2021/12/23 choose one?
    implementation("androidx.compose.material:material:1.1.0-rc01")
    implementation("androidx.compose.material3:material3:1.0.0-alpha02")
}
