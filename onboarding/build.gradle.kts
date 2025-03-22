plugins {
    id(libs.plugins.android.library.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id("core-plugin")
    id("design-plugin")
}

android {
    namespace = "dev.rarebit.kollage.onboarding"
}

dependencies {
    // Modules
    implementation(project(":core"))
    implementation(project(":design"))
    testImplementation(project(":test-utils"))
}