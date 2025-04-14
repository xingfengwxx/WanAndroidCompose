plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.btpj.lib_base"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    testApi(libs.junit)
    androidTestApi(libs.androidx.junit)
    androidTestApi(libs.androidx.espresso.core)
    androidTestApi(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestApi(platform(libs.androidx.compose.bom))
    debugApi(libs.androidx.ui.tooling)
    debugApi(libs.androidx.ui.test.manifest)

    // AndroidX
    api(libs.androidx.core.ktx)
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.ui)
    api(libs.androidx.ui.graphics)
    api(libs.androidx.ui.tooling.preview)
    api(libs.androidx.material)
    api(libs.androidx.material3)
    api(libs.androidx.runtime.livedata)
    api(libs.androidx.navigation.compose)

    api(libs.utilcodex)
    api(libs.mmkv)
    api(libs.retrofit)
    api(libs.converter.gson)
    api(libs.persistent.cookie.jar)
    api(libs.logging.interceptor)
    api(libs.coil.kt)
    api(libs.coil.kt.compose)
    api(libs.coil.kt.svg)
    api(libs.coil.kt.gif)
    api(libs.coil.kt.okhttp)
}

kapt {
    correctErrorTypes = true
}