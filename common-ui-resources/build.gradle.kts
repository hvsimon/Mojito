plugins {
    id("com.android.library")
}

android {
    namespace = "com.kiwi.common_ui_resources"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = 23
    }
}
