package plugins

import com.android.build.api.dsl.TestOptions
import com.android.build.api.variant.Variant
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.language.base.plugins.LifecycleBasePlugin
import plugins.ext.configureExtension

internal class TestConfigurationPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.afterEvaluate {
            tasks.withType(Test::class.java).forEach {
                it.useJUnitPlatform()
            }
        }
        target.configureExtension(
            configureLibraryExtension = { it.testOptions.configure() },
            configureLibraryAndroidComponentExtension = { extension ->
                extension.onVariants {
                    target.configureUnitTestTask(it)
                }
            },
            configureAppExtension = { it.testOptions.configure() },
        )
    }

    private fun TestOptions.configure() = apply {
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
        animationsDisabled = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    private fun Project.configureUnitTestTask(variant: Variant) {
        mapOf(
            "testDev${variant.name.capitalize()}UnitTest" to "test${variant.name.capitalize()}UnitTest",
            "testUat${variant.name.capitalize()}UnitTest" to "test${variant.name.capitalize()}UnitTest",
            "testProd${variant.name.capitalize()}UnitTest" to "test${variant.name.capitalize()}UnitTest",
        ).forEach { (flavourTestTaskName, testTaskName) ->
            if (project.tasks.names.contains(flavourTestTaskName)) {
                println("= ConfigureTestPlatformPlugin: Skipping $flavourTestTaskName creation as it already exists!")
            } else {
                println("= ConfigureTestPlatformPlugin: Creating $flavourTestTaskName that depends on $testTaskName")
                project.tasks.register(flavourTestTaskName) {
                    group = LifecycleBasePlugin.VERIFICATION_GROUP
                    dependsOn(testTaskName)
                }
            }
        }
    }
}