plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id("core-plugin")
}

android {
    namespace = "dev.rarebit.test_utils"
}

dependencies {
    // Modules
    implementation(project(":core"))

    // Testing
    api(libs.test.junit.jupiter)
    api(libs.test.kotest.core)
    api(libs.test.mockk.core)
    api(libs.test.mockk.android)
    api(libs.kotlinx.coroutines.test)
    api(libs.test.turbine)
}