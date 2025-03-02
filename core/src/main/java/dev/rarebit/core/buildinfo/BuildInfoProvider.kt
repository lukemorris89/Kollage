package dev.rarebit.core.buildinfo

interface BuildInfoProvider {
    val androidSdkVersion: Int
    val androidReleaseVersion: String
    val isDebugBuild: Boolean
}