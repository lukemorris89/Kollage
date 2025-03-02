plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

gradlePlugin {
    plugins {
        register("core-plugin") {
            id = "core-plugin"
            implementationClass = "plugins.CoreConfigurationPlugin"
        }
        create("detekt-plugin") {
            id = "detekt-plugin"
            implementationClass = "plugins.DetektConfigurationPlugin"
        }
    }
}

dependencies {
    implementation(libs.gradle.build.tools)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.detekt.gradle.plugin)
}