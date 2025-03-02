package dev.rarebit.core.buildinfo

import android.os.Build
import dev.rarebit.core.BuildConfig

class BuildInfoProviderImpl : BuildInfoProvider {
    override val androidSdkVersion: Int = Build.VERSION.SDK_INT

    override val androidReleaseVersion: String = Build.VERSION.RELEASE

    override val isDebugBuild: Boolean = BuildConfig.DEBUG
}
