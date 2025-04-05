package dev.rarebit.kollage.ui.createcollage.collage

import android.content.Context
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShape
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.LayerColour
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import dev.rarebit.kollage.ui.createcollage.util.crop.cropImage
import dev.rarebit.kollage.ui.createcollage.util.imageutil.flipHorizontally
import dev.rarebit.kollage.ui.createcollage.util.imageutil.rotate
import dev.rarebit.kollage.ui.createcollage.util.imageutil.toBitmap
import kotlin.math.max
import kotlin.math.min

@OptIn(ExperimentalGetImage::class)
suspend fun createCollageLayer(
    context: Context,
    imageProxy: ImageProxy,
    rect: Rect,
    cameraLensFacing: Int,
    currentCollageLayer: CollageLayer?,
    cropShape: CropShape,
    layerColour: Color,
    alpha: Float,
    onComplete: (CollageLayer) -> Unit,
) {
    if (imageProxy.image == null) return

    val rotationDegrees = imageProxy.imageInfo.rotationDegrees

    val bitmap = imageProxy.image!!.toBitmap()
        .rotate(rotationDegrees.toFloat())
        .flipHorizontally(cameraLensFacing)
        .asImageBitmap()

    val finalBitmap = ImageBitmap(
        bitmap.width,
        bitmap.height,
        ImageBitmapConfig.Argb8888,
    )
    val canvas = Canvas(finalBitmap)

    currentCollageLayer?.let {
        canvas.drawImage(
            it.image,
            Offset.Zero,
            Paint()
        )
    }

    cropImage(
        context = context,
        bitmap = bitmap,
        canvas = canvas,
        rect = rect,
        cropShape = cropShape,
        layerColour = layerColour,
        alpha = alpha,
    )

    val kollageRect = currentCollageLayer?.let {
        val previousRect = it.rect
        Rect(
            left = min(previousRect.left, rect.left),
            top = min(previousRect.top, rect.top),
            right = max(previousRect.right, rect.right),
            bottom = max(previousRect.bottom, rect.bottom)
        )
    } ?: rect

    val collageLayer = CollageLayer(
        finalBitmap,
        kollageRect,
    )

    onComplete(collageLayer)
    bitmap.asAndroidBitmap().recycle()
    imageProxy.close()
}
