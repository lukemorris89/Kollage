package dev.rarebit.kollage.data.repository.collage

import android.net.Uri
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import dev.rarebit.kollage.data.model.Collage
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
        layerColourAlpha: Float,
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
    suspend fun getAllCollages(): Flow<List<Collage>>
    suspend fun getCollage(id: Int): Collage
    suspend fun saveCollage(imagePath: String)
    suspend fun updateCollage(collage: Collage)
    suspend fun deleteCollage(collage: Collage)
    suspend fun deleteCollages(collages: List<Collage>)
}
