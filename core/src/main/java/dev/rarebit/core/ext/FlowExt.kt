package dev.rarebit.core.ext

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

@Suppress("FunctionName")
fun <T> MutableSingleSharedFlow() = MutableSharedFlow<T>(
    replay = 1,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)