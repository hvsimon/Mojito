plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("com.mikepenz.aboutlibraries.plugin")
}

if (file("google-services.json").exists()) {
    apply(plugin = "com.google.gms.google-services")
    apply(plugin = "com.google.firebase.crashlytics")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.kiwi.cocktail"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 14
        versionName = "0.3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("release/debug.keystore")
            keyPassword = "android"
            keyAlias = "androiddebugkey"
            storePassword = "android"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = ".debug"
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(project(":base"))
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
    implementation(project(":ui-browsing-history"))

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
    implementation(libs.accompanist.insetsui)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.navigation.material)

    // DI
    implementation(libs.androidx.hilt.compose)
    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)

    // UI
    implementation(libs.coil.compose)

    // Test
    testImplementation("junit:junit:4.+")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
