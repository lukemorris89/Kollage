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
        create("test-plugin") {
            id = "test-plugin"
            implementationClass = "plugins.TestConfigurationPlugin"
        }
        create("kover-plugin") {
            id = "kover-plugin"
            implementationClass = "plugins.KoverConfigurationPlugin"
        }
        register("design-plugin") {
            id = "design-plugin"
            implementationClass = "plugins.DesignConfigurationPlugin"
        }
    }
}

dependencies {
    implementation(libs.gradle.build.tools)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.detekt.gradle.plugin)
}