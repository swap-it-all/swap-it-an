plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.kotlinx.serilization) apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}
