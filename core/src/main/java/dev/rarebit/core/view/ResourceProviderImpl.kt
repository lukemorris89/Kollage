package dev.rarebit.core.view

import androidx.annotation.StringRes
import dev.rarebit.core.application.ApplicationProvider

class ResourceProviderImpl(
    applicationProvider: ApplicationProvider,
) : ResourceProvider {
    private val context = applicationProvider()

    override fun getString(
        @StringRes resId: Int,
    ): String {
        return context.getString(resId)
    }

    override fun getString(
        @StringRes resId: Int,
        vararg obj: Any,
    ): String {
        return context.getString(resId, *obj)
    }
}
