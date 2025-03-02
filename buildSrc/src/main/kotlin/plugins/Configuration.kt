package plugins

import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Configuration {
    const val COMPILE_SDK = 35
    const val TARGET_SDK = 35
    const val MIN_SDK = 31
    val JVM_TARGET = JvmTarget.JVM_21
}