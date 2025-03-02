package plugins.ext

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Project

fun Project.configureExtension(
    configureLibraryExtension: ((LibraryExtension) -> Unit)? = null,
    configureLibraryAndroidComponentExtension: ((LibraryAndroidComponentsExtension) -> Unit)? = null,
    configureAppExtension: ((ApplicationExtension) -> Unit)? = null,
) {
    val target = this
    target.plugins.all {
        when (this) {
            is LibraryPlugin -> configureLibrary(
                configureLibraryExtension,
                configureLibraryAndroidComponentExtension,
            )

            is AppPlugin -> configureApp(configureAppExtension)
        }
    }
}

private fun Project.configureApp(
    configureApp: ((ApplicationExtension) -> Unit)? = null,
) {
    configureApp?.let {
        extensions.configure(ApplicationExtension::class.java, configureApp)
    }
}

private fun Project.configureLibrary(
    configureLibrary: ((LibraryExtension) -> Unit)? = null,
    configureLibraryAndroidComponent: ((LibraryAndroidComponentsExtension) -> Unit)? = null,
) {
    configureLibrary?.let {
        extensions.configure(LibraryExtension::class.java, configureLibrary)
    }
    configureLibraryAndroidComponent?.let {
        extensions.configure(
            LibraryAndroidComponentsExtension::class.java,
            configureLibraryAndroidComponent
        )
    }
}