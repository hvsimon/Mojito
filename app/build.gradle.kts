import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.mikepenz.aboutlibraries.plugin")
}

val keystorePropertiesFile = rootProject.file("/release/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kiwi.cocktail"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("release/debug.keystore")
            keyPassword = "android"
            keyAlias = "androiddebugkey"
            storePassword = "android"
        }
        create("release") {
            storeFile = rootProject.file(keystoreProperties.getProperty("storeFile"))
            keyPassword = keystoreProperties.getProperty("keyPassword")
            keyAlias = keystoreProperties.getProperty("keyAlias")
            storePassword = keystoreProperties.getProperty("storePassword")
        }
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
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":common-ui-resources"))
    implementation(project(":common-ui-compose"))
    implementation(project(":ui-explore"))
    implementation(project(":ui-collection"))
    implementation(project(":ui-recipe"))
    implementation(project(":ui-cocktail-list"))
    implementation(project(":ui-search"))
    implementation(project(":ui-about"))
    implementation(project(":ui-ingredient"))

    // Jetbrains

    // Androidx
    implementation(libs.core)
    implementation(libs.appcompat)
    implementation(libs.bundles.activity)
    implementation(libs.navigation.compose)

    // Compose
    implementation(libs.bundles.compose)
    implementation(libs.compose.material)
    implementation(libs.compose.material3)

    // Google
    implementation(libs.material)
    implementation(libs.bundles.accompanist)
    implementation(libs.accompanist.navigation.material)

    implementation(libs.androidx.hilt.compose)
    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    // UI
    implementation(libs.coil.compose)

    // Test
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
