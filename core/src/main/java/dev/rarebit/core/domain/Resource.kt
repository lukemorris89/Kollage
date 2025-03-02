package dev.rarebit.core.domain

sealed class Resource<T> {
    abstract val data: T?

    data class Loading<T>(
        override val data: T? = null
    ) : Resource<T>()

    data class Success<T>(
        override val data: T?
    ) : Resource<T>()

    data class Error<T>(
        val error: Throwable,
        override val data: T? = null
    ) : Resource<T>()
}