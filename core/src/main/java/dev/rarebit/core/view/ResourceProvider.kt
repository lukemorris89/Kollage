package dev.rarebit.core.view

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg obj: Any): String
}

interface WithResourceProvider {
    val resourceProvider: ResourceProvider

    val @receiver:StringRes Int.asString: String
        get() = resourceProvider.getString(this)

    fun @receiver:StringRes Int.asString(vararg args: Any): String =
        resourceProvider.getString(this, *args)
}