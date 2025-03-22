plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id("core-plugin")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "dev.rarebit.kollage.navigation"
}

dependencies {
    // Modules
    implementation(project(":core"))
    testImplementation(project(":test-utils"))

    // Android Navigation Library
    api(libs.androidx.navigation.compose)
    api(libs.androidx.navigation.ui.ktx)
}