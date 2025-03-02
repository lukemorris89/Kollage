package dev.rarebit.core.viewmodel

import dev.rarebit.core.view.ViewDataResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

open class BaseViewData

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.tryEmitData(value: T): Boolean =
    tryEmit(ViewDataResource.Data(value))

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.tryEmitError(value: T): Boolean =
    tryEmit(ViewDataResource.Error(value))

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.tryEmitEmpty(): Boolean =
    tryEmit(ViewDataResource.Empty())

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.tryEmitLoading(): Boolean =
    tryEmit(ViewDataResource.Loading())

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.tryEmitLoading(value: T): Boolean =
    tryEmit(ViewDataResource.Loading(value))

suspend fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.emitData(value: T) =
    emit(ViewDataResource.Data(value))

suspend fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.emitError(value: T? = null) =
    emit(ViewDataResource.Error(value))

suspend fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.emitEmpty() =
    emit(ViewDataResource.Empty())

suspend fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.emitLoading() =
    emit(ViewDataResource.Loading())

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.dataOrNull(): T? =
    (value as? ViewDataResource.Data<T>)?.data

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.data(): T =
    dataOrNull() ?: throw NoSuchElementException("Data not available")

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.errorDataOrNull(): T? =
    (value as? ViewDataResource.Error<T>)?.errorData

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.errorData(): T =
    errorDataOrNull() ?: throw NoSuchElementException("Error Data not available")

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.updateIfData(
    whenData: (T) -> T,
) = update(
    whenData = { ViewDataResource.Data(whenData.invoke(data)) },
)

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.updateIfError(
    whenError: (ViewDataResource.Error<T>.() -> ViewDataResource<T>)? = null,
) = update(whenError = whenError)

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.updateIfLoading(
    whenLoading: (ViewDataResource.Loading<T>.() -> ViewDataResource<T>)? = null,
) = update(whenLoading = whenLoading)

fun <T : BaseViewData, E : MutableStateFlow<ViewDataResource<T>>> E.update(
    whenData: (ViewDataResource.Data<T>.() -> ViewDataResource<T>)? = null,
    whenEmpty: (ViewDataResource.Empty<T>.() -> ViewDataResource<T>)? = null,
    whenError: (ViewDataResource.Error<T>.() -> ViewDataResource<T>)? = null,
    whenLoading: (ViewDataResource.Loading<T>.() -> ViewDataResource<T>)? = null,
) = update {
    when (it) {
        is ViewDataResource.Data -> whenData?.invoke(it) ?: it
        is ViewDataResource.Empty -> whenEmpty?.invoke(it) ?: it
        is ViewDataResource.Error -> whenError?.invoke(it) ?: it
        is ViewDataResource.Loading -> whenLoading?.invoke(it) ?: it
    }
}
