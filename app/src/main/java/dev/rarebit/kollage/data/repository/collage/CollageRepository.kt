package dev.rarebit.kollage.data.repository.collage

import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat
import kotlinx.coroutines.flow.StateFlow

interface CollageRepository {
    val collageBackground: ImageBitmap?
    val finalCollage: ImageBitmap?
    val imageFormat: StateFlow<ImageFormat>

    fun updateImageFormat(imageFormat: ImageFormat)
    fun updateCollageBackground(collageBackground: ImageBitmap)
    fun updateFinalCollage(finalCollage: ImageBitmap?)
    fun clearImage()
    suspend fun saveCollageToLocalStorage(
        bitmap: ImageBitmap,
        imageFormat: ImageFormat
    ): Uri
}
