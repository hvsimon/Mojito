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
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.coroutines.core)

    implementation(libs.paging.runtime)
    implementation(libs.paging.common)

    implementation(libs.dagger.dagger)
    kapt(libs.dagger.compiler)
    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)
}
