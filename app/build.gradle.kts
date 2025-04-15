import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.wangxingxing.wanandroidcompose"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.wangxingxing.wanandroidcompose"
        minSdk = 24
        targetSdk = 35
        versionCode = 100
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        viewBinding = true
    }

    applicationVariants.all {
        outputs.all {
            val buildType = buildType.name
            val versionCode = defaultConfig.versionCode
            val versionName = defaultConfig.versionName
            val currentTime = getCurrentTime()
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName =
                "WanAndroidCompose-${buildType}-v${versionCode}-${versionName}-${currentTime}.apk"
        }
    }
}

fun getCurrentTime(): String {
    val dateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
    dateFormat.timeZone = TimeZone.getTimeZone("GMT+8")
    return dateFormat.format(Date())
}


dependencies {

    implementation(project(":lib_base"))

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.bugly.update)
    implementation(libs.bugly.nativecrashreport)
    implementation(libs.accompanist.webview)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.lottie.compose)

}