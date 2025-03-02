package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension

@ExperimentalStdlibApi
class ConfigureKoverPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        val catalog = target.extensions
            .getByType(VersionCatalogsExtension::class.java)
            .named("libs")
        val koverPlugin = catalog.findPlugin("kover").get().get().pluginId.orEmpty()

        target.plugins.apply(koverPlugin)
    }
}