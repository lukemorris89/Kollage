package plugins

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider

@ExperimentalStdlibApi
class DetektConfigurationPlugin : Plugin<Project> {
    private lateinit var detektPlugin: String
    private lateinit var detektComposePlugin: Provider<MinimalExternalModuleDependency>
    private lateinit var detektFormattingPlugin: Provider<MinimalExternalModuleDependency>

    override fun apply(target: Project) {
        val catalog = target.extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")
        detektPlugin = catalog.findLibrary("detekt-gradle-plugin").get().get().group.orEmpty()
        detektComposePlugin = catalog.findLibrary("detekt-compose").get()
        detektFormattingPlugin = catalog.findLibrary("detekt-formatting").get()

        target.configureDetekt()
    }

    private fun Project.configureDetekt() {
        plugins.apply(detektPlugin)
        plugins.withId(detektPlugin) {
            extensions.configure(DetektExtension::class.java) {
                // Define the detekt configuration(s) you want to use.
                // Defaults to the default detekt configuration.
                config.setFrom(files("${project.rootDir}/.detekt/config.yml"))
                buildUponDefaultConfig = true
                // Specifying a baseline file. All findings stored in this file in subsequent runs of detekt.
                baseline = file("${project.rootDir}/.detekt/baseline.xml")
                // Adds debug output during task execution. `false` by default.
                debug = true
                autoCorrect = true
            }
        }
        dependencies.addProvider("detektPlugins", detektComposePlugin)
        dependencies.addProvider("detektPlugins", detektFormattingPlugin)
    }
}