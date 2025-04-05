package dev.rarebit.kollage.data.repository.collage

import androidx.compose.ui.graphics.ImageBitmap
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat
import kotlinx.coroutines.flow.StateFlow

interface CollageRepository {
    val collage: StateFlow<CollageLayer?>
    val previousCollage: StateFlow<CollageLayer?>
    val collageBackground: ImageBitmap?
    val finalCollage: StateFlow<ImageBitmap?>
    val imageFormat: StateFlow<ImageFormat>

    fun updateImageFormat(imageFormat: ImageFormat)
    fun updatePreviousCollageLayer(collage: CollageLayer?)
    fun updateCollageLayer(collage: CollageLayer?)
    fun updateCollageBackground(collageBackground: ImageBitmap?)
    fun updateFinalCollage(finalCollage: ImageBitmap?)
    fun clearImage()
}
