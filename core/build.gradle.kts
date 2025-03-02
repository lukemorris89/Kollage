plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id("core-plugin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.rarebit.core"
    buildToolsVersion = libs.versions.androidBuildTools.get()
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    //DI
    api(libs.koin.android)
    api(libs.koin.android.compose)
    androidTestImplementation(libs.koin.test)

    // Kotlin immutable collections
    api(libs.kotlinx.collections.immutable)

    // Kotlin coroutines
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)

    // Android Navigation Library
    api(libs.androidx.navigation.compose)
    api(libs.androidx.navigation.ui.ktx)

    // Logging
    api(libs.timber)

    // Serialization
    api(libs.kotlinx.serialization.json)

    // Linting
    api(libs.detekt.gradle.plugin)
}