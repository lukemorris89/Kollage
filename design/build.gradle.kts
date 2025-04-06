plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id("design-plugin")
}

android {
    namespace = "dev.rarebit.design"
    kotlinOptions {
        kotlinOptions.freeCompilerArgs += listOf(
            "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
            "-opt-in=androidx.compose.ui.ExperimentalComposeUiApi",
        )
    }
}

dependencies {
    // Modules
    implementation(project(":core"))
    testImplementation(project(":test-utils"))

    // Jetpack Compose
    api(platform(libs.compose.bom))
    api(libs.compose.ui)
    api(libs.compose.ui.tooling)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.ui.animation)
    api(libs.compose.ui.foundation)

    // Material design
    api(libs.compose.material3)

    // Koin
    api(libs.koin.android.compose)

    // BottomSheet
    api(libs.bottomSheetDialog)

    // Image loading
    api(libs.coil.compose)
    api(libs.coil.network.okhttp)
}