package dev.rarebit.kollage.ui.createcollage.util.motion

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventPass.Main
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.pointerMotionEvents(
    onDown: (PointerInputChange) -> Unit = {},
    onMove: (PointerInputChange) -> Unit = {},
    onUp: (PointerInputChange) -> Unit = {},
    delayAfterDownInMillis: Long = 0L,
    requireUnconsumed: Boolean = true,
    pass: PointerEventPass = Main,
    key1: Any? = Unit
) = this.then(
    Modifier.pointerInput(key1) {
        detectMotionEvents(
            onDown,
            onMove,
            onUp,
            delayAfterDownInMillis,
            requireUnconsumed,
            pass
        )
    }
)
