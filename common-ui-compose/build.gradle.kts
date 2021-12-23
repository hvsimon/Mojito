plugins {
    id("com.android.library")
    id("kotlin-android")
}

android{
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }
}

dependencies {
    // Compose
    implementation(libs.bundles.compose)
    // TODO: 2021/12/23 choose one?
    implementation("androidx.compose.material:material:1.1.0-rc01")
    implementation("androidx.compose.material3:material3:1.0.0-alpha02")
}