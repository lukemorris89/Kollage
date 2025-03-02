import com.google.firebase.appdistribution.gradle.AppDistributionExtension
import java.util.Properties

plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id("core-plugin")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.google.services)
    id(libs.plugins.firebase.appdistribution.get().pluginId)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.ktlint)
}

val appVersionCode: Int
    get() = getEnvironmentPropertyOrNull("APP_VERSION_CODE")?.toInt()
        ?: getFilePropertyOrNull("app_version.properties", "APP_VERSION_CODE")?.toInt()
        ?: 1

val appVersionName: String
    get() = getEnvironmentPropertyOrNull("APP_VERSION_NAME")
        ?: getFileProperty("app_version.properties", "APP_VERSION_NAME")

android {
    namespace = "dev.rarebit.kollage"

    defaultConfig {
        applicationId = "dev.rarebit.kollage"
        versionCode = appVersionCode
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        // Keystore file is not in the project, encoded base64 version of it is stored in GitHub secrets.
        // Text version is encoded with command base64 --i keystore.jks --o keystore.txt
        register("production") {
            storeFile = file("keystore.jks")
            storePassword = getEnvironmentPropertyOrNull("STORE_PASSWORD")
            keyAlias = getEnvironmentPropertyOrNull("KEY_ALIAS")
            keyPassword = getEnvironmentPropertyOrNull("KEY_PASSWORD")
        }
        register("internal") {
            storeFile = file("keystore.jks")
            storePassword = getEnvironmentPropertyOrNull("STORE_PASSWORD")
            keyAlias = getEnvironmentPropertyOrNull("KEY_ALIAS")
            keyPassword = getEnvironmentPropertyOrNull("KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            versionNameSuffix = "-debug"
            enableUnitTestCoverage = true
            configure<AppDistributionExtension> {
                serviceCredentialsFile = "app/google-application-credentials.json"
                appId = getEnvironmentPropertyOrNull("APP_DISTRIBUTION_APP_ID")
                artifactType = "APK"
            }
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            configure<AppDistributionExtension> {
                serviceCredentialsFile = "app/google-application-credentials.json"
                appId = getEnvironmentPropertyOrNull("APP_DISTRIBUTION_APP_ID")
                artifactType = "APK"
                groups = "mobile-devs"
            }
        }
    }

    flavorDimensions += "version"

    productFlavors {
        create("dev") {
            dimension = "version"

            configure<AppDistributionExtension> {
                releaseNotes = "Git action Development Build version: ${defaultConfig.versionCode}"
            }

            applicationIdSuffix = ".dev"
            signingConfig = signingConfigs.getByName("internal")
        }

        create("uat") {
            dimension = "version"

            configure<AppDistributionExtension> {
                releaseNotes = "Git action UAT Build version: ${defaultConfig.versionCode}"
            }
            applicationIdSuffix = ".uat"
            signingConfig = signingConfigs.getByName("internal")
        }

        create("prod") {
            dimension = "version"

            configure<AppDistributionExtension> {
                releaseNotes = "Git action Production Build version: ${defaultConfig.versionCode}"
            }
            signingConfig = signingConfigs.getByName("production")
        }
    }

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/AL2.0",
                "META-INF/LGPL2.1",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            ),
        )
    }
}

fun getFilePropertyOrNull(
    file: String,
    property: String,
): String? =
    Properties().let {
        runCatching {
            it.load(project.rootProject.file(file).inputStream())
            it.getProperty(property)
        }.getOrNull()
    }

fun getFileProperty(
    file: String,
    property: String,
): String {
    return getFilePropertyOrNull(file, property)
        ?: throw IllegalArgumentException("$property property not available or file $file not available!")
}

fun getEnvironmentLocalPropertyOrNull(property: String): String? =
    getFilePropertyOrNull("local.properties", property)

fun getEnvironmentSystemPropertyOrNull(property: String): String? =
    runCatching {
        System.getenv(property)
    }.getOrNull()

fun getEnvironmentPropertyOrNull(property: String): String? {
    return getEnvironmentSystemPropertyOrNull(property)
        ?: getEnvironmentLocalPropertyOrNull(property)
}

fun getEnvironmentProperty(property: String): String {
    return getEnvironmentPropertyOrNull(property)
        ?: throw IllegalArgumentException("$property environment property not available!")
}

dependencies {
    // Modules
    implementation(project(":core"))
    implementation(project(":test-utils"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.test.espresso.intents)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.test.rules)
    androidTestUtil(libs.test.orchestrator)

    debugImplementation(libs.leakcanary.android)
}