package plugins

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CompileOptions
import com.android.build.api.dsl.LibraryDefaultConfig
import com.android.build.api.dsl.LibraryExtension
import plugins.ext.configureExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import plugins.Configuration.COMPILE_SDK
import plugins.Configuration.JVM_TARGET
import plugins.Configuration.MIN_SDK
import plugins.Configuration.TARGET_SDK

@ExperimentalStdlibApi
class CoreConfigurationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.configureExtension(
            configureLibraryExtension = configureLibrary,
            configureAppExtension = configureApp,
        )
        target.tasks.withType(KotlinCompile::class.java) {
            compilerOptions {
                jvmTarget.set(JVM_TARGET)
                freeCompilerArgs.set(listOf("-Xcontext-receivers"))
            }
        }
        target.plugins.apply("detekt-plugin")
    }

    private val configureLibrary: (LibraryExtension) -> Unit = { library ->
        library.apply {
            compileSdk = COMPILE_SDK
            compileOptions.java()
            defaultConfig.configure()
            buildFeatures.buildConfig = false
        }
    }

    private val configureApp: (ApplicationExtension) -> Unit = { app ->
        app.apply {
            compileSdk = COMPILE_SDK
            compileOptions.java()
            defaultConfig.configure()
            buildFeatures {
                compose = true
                buildConfig = true
            }
        }
    }

    private fun CompileOptions.java() = apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    private fun LibraryDefaultConfig.configure() = apply {
        minSdk = MIN_SDK
        vectorDrawables.useSupportLibrary = true
    }

    private fun ApplicationDefaultConfig.configure() = apply {
        minSdk = MIN_SDK
        targetSdk = TARGET_SDK
        vectorDrawables.useSupportLibrary = true
    }
}