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

    signingConfigs {
        create("release") {
            storeFile = file("wanandroid.jks") // 密钥库文件路径
            storePassword = "wxx666" // 密钥库密码
            keyAlias = "wanandroid" // 密钥别名
            keyPassword = "wxx666" // 密钥密码
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

// 使用 强制版本（Force Version）
configurations.all {
    resolutionStrategy {
        force(
            "org.jetbrains.kotlin:kotlin-stdlib:2.1.10",
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1",
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1"
        )
    }
}

dependencies {

    // 使用 Kotlin BOM
    implementation(platform(libs.kotlin.bom))
    // 使用协程 BOM
    implementation(platform(libs.kotlinx.coroutines.bom))

    // 使用依赖约束,统一指定版本
//    constraints {
//        implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.10")
//        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
//        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")
//    }

    implementation(project(":lib_base"))

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.bugly.update)
    implementation(libs.bugly.nativecrashreport)
    implementation(libs.accompanist.webview)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.lottie.compose)

    // material icons扩展库，会增apk大体积
    implementation(libs.androidx.compose.material.iconsExtended)


}