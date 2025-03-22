package plugins

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

@ExperimentalStdlibApi
class DesignConfigurationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val catalog = target.extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")
        val composeCompilerPlugin =
            catalog.findPlugin("kotlin-compose").get().get().pluginId.orEmpty()
        target.plugins.apply("core-plugin")
        target.plugins.apply(composeCompilerPlugin)
        target.plugins.all {
            when (this) {
                is LibraryPlugin -> {
                    target.extensions.configure(LibraryExtension::class.java, configureLibrary)
                }

                is AppPlugin -> {
                    target.extensions.configure(ApplicationExtension::class.java, configureApp)
                }
            }
        }
    }

    private val configureLibrary: (LibraryExtension) -> Unit = { library ->
        library.apply {
            buildFeatures.compose = true
        }
    }

    private val configureApp: (ApplicationExtension) -> Unit = { app ->
        app.apply {
            buildFeatures.compose = true
        }
    }
}