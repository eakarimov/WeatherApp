plugins {
    alias(libs.plugins.androidLibrary)
}

android {
    namespace = "dev.eakarimov.data"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(libs.javax.inject)

    implementation(project(":core:weather-api"))
}