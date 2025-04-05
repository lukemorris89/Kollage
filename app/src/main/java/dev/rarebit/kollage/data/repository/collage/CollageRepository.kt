package dev.rarebit.kollage.data.repository.collage

import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import dev.rarebit.kollage.data.database.model.CollageData
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShape
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CollageRepository {
    val collageBackground: ImageBitmap?
    val finalCollage: ImageBitmap?
    val imageFormat: StateFlow<ImageFormat>

    fun updateImageFormat(imageFormat: ImageFormat)
    fun updateCollageBackground(collageBackground: ImageBitmap)
    suspend fun updateCollage(
        imageProxy: ImageProxy,
        rect: Rect,
        cameraLensFacing: Int,
        currentCollageLayer: CollageLayer?,
        cropShape: CropShape,
        layerColour: Color,
        alpha: Float,
        onComplete: (CollageLayer) -> Unit,
    )
    suspend fun updateFinalCollage(
        background: ImageBitmap,
        collage: ImageBitmap?,
    )
    fun clearImage()
    suspend fun saveCollageToLocalStorage(
        bitmap: ImageBitmap,
        imageFormat: ImageFormat
    ): Uri
    fun getAllCollages(): Flow<List<CollageData>>
    suspend fun getCollage(id: Int): CollageData
    suspend fun saveCollage(imagePath: String)
    suspend fun updateCollage(collage: CollageData)
    suspend fun deleteCollage(collage: CollageData)
}
