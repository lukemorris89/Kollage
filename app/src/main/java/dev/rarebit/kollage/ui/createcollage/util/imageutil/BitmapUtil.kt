package dev.rarebit.kollage.ui.createcollage.util.imageutil

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.Image
import androidx.camera.core.CameraSelector
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asAndroidBitmap
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Locale

private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

fun convertPixelsToDp(px: Float, context: Context): Float {
    val density = context.resources.displayMetrics.densityDpi
    return px / density
}

fun Image.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    buffer.rewind()
    val bytes = ByteArray(buffer.capacity())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}

fun Bitmap.rotate(degrees: Float): Bitmap =
    Bitmap.createBitmap(this, 0, 0, width, height, Matrix().apply { postRotate(degrees) }, true)

fun Bitmap.flipHorizontally(lensFacing: Int): Bitmap {
    return if (lensFacing == CameraSelector.LENS_FACING_FRONT) {
        val matrix = Matrix()
        matrix.preScale(-1.0f, 1.0f)

        return Bitmap.createBitmap(
            this,
            0,
            0,
            this.width,
            this.height,
            matrix,
            true
        )
    } else {
        this
    }
}

fun drawKollageBitmap(
    context: Context,
    background: ImageBitmap?,
    collageLayer: CollageLayer?
): ImageBitmap? {
    return if (background != null) {
        val canvas = Canvas(background)

        if (collageLayer != null) {
            canvas.drawImage(
                collageLayer.image,
                topLeftOffset = Offset(
                    convertPixelsToDp(collageLayer.rect.left, context),
                    convertPixelsToDp(collageLayer.rect.top, context),
                ),
                Paint(),
            )
        }
        background
    } else {
        null
    }
}

fun generateImage(
    context: Context,
    backgroundBitmap: ImageBitmap,
    kollage: CollageLayer?,
    backgroundSelection: BackgroundSelection,
    backgroundColor: Color,
): ImageBitmap? {
    return if (backgroundSelection == BackgroundSelection.COLOR) {
        val paint = Paint().apply {
            color = backgroundColor
        }

        val colorBitmap = ImageBitmap(
            backgroundBitmap.width,
            backgroundBitmap.height,
            backgroundBitmap.config
        )
        val canvas = Canvas(image = colorBitmap)
        canvas.drawRect(
            0f,
            0f,
            backgroundBitmap.width.toFloat(),
            backgroundBitmap.height.toFloat(),
            paint
        )
        drawKollageBitmap(context, colorBitmap, kollage)
    } else {
        drawKollageBitmap(context, backgroundBitmap, kollage)
    }
}

fun convertBitmapToFile(
    bitmap: ImageBitmap,
    outputDirectory: File,
    imageFormat: ImageFormat,
): File {
    val filenameFormat = FILENAME_FORMAT
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(
            filenameFormat,
            Locale.ENGLISH
        ).format(System.currentTimeMillis()) + imageFormat.value
    )

    val out = FileOutputStream(photoFile)
    bitmap.asAndroidBitmap().compress(imageFormat.compressionFormat, 100, out)

    out.flush()
    out.close()

    return photoFile
}
