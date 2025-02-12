import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ktlint)
}

val properties =
    Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

android {
    namespace = "com.example.swap_it"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.swap_it"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            "String",
            "KAKAO_NATIVE_APP_KEY",
            properties.getProperty("KAKAO_NATIVE_APP_KEY"),
        )
        buildConfigField(
            "String",
            "SWAP_IT_BASE_URL",
            properties.getProperty("SWAP_IT_BASE_URL"),
        )
        resValue(
            "string",
            "kakao_oauth_host",
            properties.getProperty("kakao_oauth_host"),
        )
        resValue(
            "string",
            "google_client_id",
            properties.getProperty("google_client_id"),
        )

        buildFeatures {
            buildConfig = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

configurations.implementation {
    exclude(group = "com.intellij", module = "annotations")
}

dependencies {
    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.json)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlin.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Room (Database)
    implementation(libs.room)
    implementation(libs.room.ktx)
    implementation(libs.room.compiler)

    // Navigation
    implementation("androidx.compose.material:material:1.7.7")
    implementation(libs.androidx.navigation.compose)
    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.serialization.converter)
    // Login
    implementation(libs.kakao)
    implementation(libs.google)
    implementation(libs.googleid)
}
