plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "dev.eakarimov.uikit"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    api(libs.material)
}