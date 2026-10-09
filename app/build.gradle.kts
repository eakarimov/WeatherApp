plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "dev.eakarimov.weatherapp"
    compileSdk = 37

    defaultConfig {
        applicationId = "dev.eakarimov.weatherapp"
        minSdk = 24
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "WEATHER_API_KEY", "\"f66c632b485c4ed8be375100250412\"")
        buildConfigField("String", "WEATHER_API_BASE_URL", "\"https://api.weatherapi.com/v1/\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.appcompat)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    coreLibraryDesugaring(libs.desugar.jdk.libs)

    implementation(project(":core:weather-api"))
    implementation(project(":core:uikit"))
    implementation(project(":feature:forecast:ui"))
}