plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composecompiler.get()
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":common-ui-resources"))
    implementation(project(":common-ui-compose"))

    // Jetbrains

    // Androidx
    implementation(libs.core)
    implementation(libs.appcompat)
    implementation(libs.bundles.activity)
    implementation(libs.navigation.compose)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    implementation(libs.paging.runtime)
    implementation(libs.paging.common)
    implementation(libs.paging.compose)

    // Compose
    implementation(libs.bundles.compose)
    // TODO: 2021/12/23 choose one?
    implementation("androidx.compose.material:material:1.1.0-rc01")
    implementation("androidx.compose.material3:material3:1.0.0-alpha02")

    // Google
    implementation(libs.material)
    implementation(libs.bundles.accompanist)

    implementation(libs.androidx.hilt.compose)
    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    // UI
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)

    // Test
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.1.0-rc01")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.1.0-rc01")
}
