plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id("core-plugin")
}

android {
    namespace = "dev.rarebit.test_utils"
    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            ),
        )
    }
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