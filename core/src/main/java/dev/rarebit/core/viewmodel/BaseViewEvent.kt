package dev.rarebit.core.viewmodel

import dev.rarebit.core.ext.MutableSingleSharedFlow
import dev.rarebit.core.view.ViewEvent
import kotlinx.coroutines.flow.MutableSharedFlow

open class BaseViewEvent

fun <T : BaseViewEvent> viewEventFlow() = MutableSingleSharedFlow<ViewEvent<T>>()

fun <T : BaseViewEvent, E : MutableSharedFlow<ViewEvent<T>>> E.tryEmit(value: T): Boolean =
    tryEmit(ViewEvent(value))

suspend fun <T : BaseViewEvent, E : MutableSharedFlow<ViewEvent<T>>> E.emit(value: T) =
    emit(ViewEvent(value))

fun <T : BaseViewEvent> T.tryEmitIn(emitter: MutableSharedFlow<ViewEvent<T>>) =
    emitter.tryEmit(ViewEvent(this))

suspend fun <T : BaseViewEvent> T.emitIn(emitter: MutableSharedFlow<ViewEvent<T>>) =
    emitter.emit(ViewEvent(this))
