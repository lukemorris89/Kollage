package dev.rarebit.kollage.ui.createcollage.util.crop

import android.content.Context
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.min

fun calculateImageHeightRatio(context: Context, bitmapHeight: Int): Float {
    val displayMetrics = context.resources.displayMetrics
    val screenHeight = displayMetrics.heightPixels
    return bitmapHeight / screenHeight.toFloat()
}

fun calculateImageWidthRatio(context: Context, bitmapWidth: Int): Float {
    val displayMetrics = context.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels
    return bitmapWidth / screenWidth.toFloat()
}

suspend fun cropImage(
    context: Context,
    canvas: Canvas,
    bitmap: ImageBitmap,
    cropShape: CropShape,
    layerColour: Color,
    alpha: Float,
    rect: Rect,
) {
    withContext(Dispatchers.Default) {
        val imageWidthRatio = calculateImageWidthRatio(context, bitmap.width)
        val imageHeightRatio = calculateImageHeightRatio(context, bitmap.height)

        val dstRect = Rect(
            Offset(
                (rect.left * imageWidthRatio),
                (rect.top * imageHeightRatio),
            ),
            Size(
                ((rect.width) * imageWidthRatio),
                ((rect.height) * imageHeightRatio),
            ),
        )

        val tempBitmap = ImageBitmap(bitmap.width, bitmap.height, bitmap.config)
        val tempCanvas = Canvas(tempBitmap)

        val paint = Paint().apply {
            color = Color.Transparent
        }

        tempCanvas.drawRect(dstRect, paint)

        val path = Path()
        when (cropShape) {
            CropShape.RECTANGLE -> path.addRect(dstRect)
            CropShape.CIRCLE -> path.addOval(dstRect)
        }

        tempCanvas.clipPath(path)

        tempCanvas.drawImage(
            bitmap,
            Offset.Zero,
            Paint()
        )

        tempCanvas.drawRect(
            dstRect,
            Paint().apply {
                color = if (layerColour == Color.Transparent) {
                    layerColour
                } else {
                    layerColour.copy(alpha = alpha)
                }
                style = PaintingStyle.Fill
            }
        )

        canvas.drawImage(tempBitmap, Offset.Zero, Paint())
    }
}

fun DrawScope.drawCropShape(startPosition: Offset, currentPosition: Offset, cropShape: CropShape) {
    val width = currentPosition.x - startPosition.x
    val height = currentPosition.y - startPosition.y
    when (cropShape) {
        CropShape.RECTANGLE -> {
            drawRect(
                color = Color.Gray,
                topLeft = startPosition,
                size = Size(width, height),
                style = Stroke(5f),
            )

//            drawRectGrid(startPosition, currentPosition)

            drawCornersPath(
                left = min(startPosition.x, currentPosition.x),
                top = min(startPosition.y, currentPosition.y),
                right = max(startPosition.x, currentPosition.x),
                bottom = max(startPosition.y, currentPosition.y),
            )
        }

        CropShape.CIRCLE -> {
            drawOval(
                color = Color.Gray,
                topLeft = startPosition,
                size = Size(width, height),
                style = Stroke(5f),
            )

//            drawOvalGrid(startPosition, currentPosition)

            drawArcsPath(
                left = min(startPosition.x, currentPosition.x),
                top = min(startPosition.y, currentPosition.y),
                right = max(startPosition.x, currentPosition.x),
                bottom = max(startPosition.y, currentPosition.y),
            )
        }
    }
}

private fun DrawScope.drawCornersPath(
    left: Float,
    top: Float,
    right: Float,
    bottom: Float,
    cornerWidth: Float = 20f,
) {
    val path = Path()

    path.moveTo(left, (top + cornerWidth))
    path.lineTo(left, top)
    path.lineTo((left + cornerWidth), top)

    path.moveTo((right - cornerWidth), top)
    path.lineTo(right, top)
    path.lineTo(right, (top + cornerWidth))

    path.moveTo(left, (bottom - cornerWidth))
    path.lineTo(left, bottom)
    path.lineTo((left + cornerWidth), bottom)

    path.moveTo((right - cornerWidth), bottom)
    path.lineTo(right, bottom)
    path.lineTo(right, (bottom - cornerWidth))

    drawPath(
        path = path,
        color = Color.White,
        style = Stroke(
            width = 8f,
        )
    )
}

private fun DrawScope.drawArcsPath(
    left: Float,
    top: Float,
    right: Float,
    bottom: Float,
    arcDegrees: Float = 30f
) {
    val rect = Rect(
        left = left,
        top = top,
        right = right,
        bottom = bottom,
    )
    val path = Path()
    with(path) {
        moveTo((right - left) / 2, top)
        arcTo(rect, 180f - (arcDegrees / 2), arcDegrees, true)

        moveTo(right, (bottom - top) / 2)
        arcTo(rect, 270f - (arcDegrees / 2), arcDegrees, true)

        moveTo((right - left) / 2, bottom)
        arcTo(rect, 0f - (arcDegrees / 2), arcDegrees, true)

        moveTo(left, (bottom - top) / 2)
        arcTo(rect, 90f - (arcDegrees / 2), arcDegrees, true)
    }

    drawPath(
        path = path,
        color = Color.White,
        style = Stroke(
            width = 8f,
        )
    )
}
