package dev.rarebit.core.view

import dev.rarebit.core.domain.Resource
import dev.rarebit.core.viewmodel.BaseViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

suspend fun <DomainData, ViewData> Resource<DomainData>.toViewDataResource(
    mapper: suspend (DomainData) -> ViewData,
    onError: ((errorData: Resource.Error<DomainData>?) -> ViewData?)? = null
): ViewDataResource<ViewData> {
    return when (this) {
        is Resource.Error -> ViewDataResource.Error(errorData = onError?.invoke(this))

        is Resource.Loading -> ViewDataResource.Loading()

        is Resource.Success ->
            if (this.data == null || (this.data as? Collection<*>)?.isEmpty() == true) {
                ViewDataResource.Empty()
            } else {
                runCatching {
                    ViewDataResource.Data(data = mapper(this.data!!))
                }.getOrElse {
                    ViewDataResource.Error(errorData = onError?.invoke(null))
                }
            }
    }
}

sealed class ViewDataResource<T> : BaseViewData() {
    class Empty<T> : ViewDataResource<T>()
    data class Loading<T>(val data: T? = null) : ViewDataResource<T>()
    data class Data<T>(val data: T) : ViewDataResource<T>()
    data class Error<T>(val errorData: T? = null) : ViewDataResource<T>()
}

interface ViewDataResourceObserver<ViewData> {
    suspend fun onEmpty()
    suspend fun onData(viewData: ViewData?)
    suspend fun onLoading()
    suspend fun onError(errorViewData: ViewData?)
}

typealias ViewDataResourceStateFlow<T> = StateFlow<ViewDataResource<T>>

fun <T : BaseViewData> viewDataResourceFlow(initialValue: T) =
    MutableStateFlow<ViewDataResource<T>>(ViewDataResource.Data(initialValue))

fun <T : BaseViewData> viewDataResourceFlow(initialValue: ViewDataResource<T> = ViewDataResource.Loading()) =
    MutableStateFlow(initialValue)
