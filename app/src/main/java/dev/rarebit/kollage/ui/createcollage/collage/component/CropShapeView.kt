package dev.rarebit.kollage.ui.createcollage.collage.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.input.pointer.PointerInputChange
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import dev.rarebit.kollage.ui.createcollage.util.crop.drawCropShape
import dev.rarebit.kollage.ui.createcollage.util.motion.MotionEvent
import dev.rarebit.kollage.ui.createcollage.util.motion.pointerMotionEvents
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@Composable
fun CropShapeView(
    viewData: CreateCollageViewData,
    onStart: () -> Unit,
    onRelease: (Rect) -> Unit,
) {
    var motionEvent by remember { mutableStateOf(MotionEvent.Idle) }
    var startPosition by remember { mutableStateOf(Offset.Unspecified) }
    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }

    val drawModifier = Modifier
        .fillMaxSize()
        .pointerMotionEvents(
            onDown = { pointerInputChange: PointerInputChange ->
                startPosition = pointerInputChange.position
                currentPosition = pointerInputChange.position
                motionEvent = MotionEvent.Down
                pointerInputChange.consume()
            },
            onMove = { pointerInputChange: PointerInputChange ->
                currentPosition = pointerInputChange.position
                motionEvent = MotionEvent.Move
                pointerInputChange.consume()
            },
            onUp = { pointerInputChange: PointerInputChange ->
                motionEvent = MotionEvent.Up
                pointerInputChange.consume()
            },
            delayAfterDownInMillis = 25L
        )

    Canvas(modifier = drawModifier) {
        when (motionEvent) {
            MotionEvent.Down -> {
                drawCropShape(
                    startPosition = startPosition,
                    currentPosition = startPosition,
                    viewData.selectedCropShape,
                )
                onStart()
            }

            MotionEvent.Move -> {
                drawCropShape(
                    startPosition = startPosition,
                    currentPosition = currentPosition,
                    viewData.selectedCropShape,
                )
            }

            MotionEvent.Up -> {
                val width = abs(currentPosition.x - startPosition.x)
                val height = abs(currentPosition.y - startPosition.y)
                val startX = max(min(startPosition.x, currentPosition.x), 0f)
                val startY = max(min(startPosition.y, currentPosition.y), 0f)

                onRelease(
                    Rect(
                        Offset(startX, startY),
                        Size(width, height)
                    )
                )
                currentPosition = Offset.Unspecified
                motionEvent = MotionEvent.Idle
            }

            else -> Unit
        }
    }
}
