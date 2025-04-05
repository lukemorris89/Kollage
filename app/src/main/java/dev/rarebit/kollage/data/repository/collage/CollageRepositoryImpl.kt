package dev.rarebit.kollage.data.repository.collage

import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import dev.rarebit.core.application.ApplicationContextProvider
import dev.rarebit.kollage.data.database.model.CollageData
import dev.rarebit.kollage.data.datasource.local.LocalDataSource
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShape
import dev.rarebit.kollage.ui.createcollage.collage.createCollageLayer
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.IOException

class CollageRepositoryImpl(
    private val applicationContextProvider: ApplicationContextProvider,
    private val localDataSource: LocalDataSource,
) : CollageRepository {
    override var collageBackground: ImageBitmap? = null
    override var finalCollage: ImageBitmap? = null

    private val _imageFormat = MutableStateFlow(ImageFormat.PNG)
    override val imageFormat = _imageFormat.asStateFlow()

    override fun updateImageFormat(imageFormat: ImageFormat) {
        _imageFormat.value = imageFormat
    }

    override fun updateCollageBackground(background: ImageBitmap) {
        collageBackground = background
    }

    override suspend fun updateCollage(
        imageProxy: ImageProxy,
        rect: Rect,
        cameraLensFacing: Int,
        currentCollageLayer: CollageLayer?,
        cropShape: CropShape,
        layerColour: Color,
        alpha: Float,
        onComplete: (CollageLayer) -> Unit
    ) {
        createCollageLayer(
            context = applicationContextProvider(),
            imageProxy = imageProxy,
            rect = rect,
            cameraLensFacing = cameraLensFacing,
            currentCollageLayer = currentCollageLayer,
            cropShape = cropShape,
            layerColour = layerColour,
            alpha = alpha,
            onComplete = {
                onComplete(it)
            }
        )
    }

    override suspend fun updateFinalCollage(background: ImageBitmap, collage: ImageBitmap?) {
        collageBackground = background
        finalCollage = collage
    }

    override fun clearImage() {
        collageBackground = null
    }

    override suspend fun saveCollageToLocalStorage(
        bitmap: ImageBitmap,
        imageFormat: ImageFormat
    ): Uri = withContext(Dispatchers.IO) {
        val androidBitmap = bitmap.asAndroidBitmap()

        val filename = "Kollage_${System.currentTimeMillis()}${imageFormat.value}"

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, imageFormat.mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Kollage")
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        val contentResolver = applicationContextProvider().contentResolver
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            ?: throw IOException("Failed to create new MediaStore record.")

        contentResolver.openOutputStream(uri)?.use { out ->
            if (!androidBitmap.compress(imageFormat.compressionFormat, 100, out)) {
                throw IOException("Failed to save bitmap.")
            }
        } ?: throw IOException("Unable to open output stream for URI: $uri")

        contentValues.clear()
        contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
        contentResolver.update(uri, contentValues, null, null)

        localDataSource.saveCollage(uri.toString())
        uri
    }

    override suspend fun saveCollage(imagePath: String) =
        localDataSource.saveCollage(imagePath)

    override fun getAllCollages() = localDataSource.getAllCollages()

    override suspend fun getCollage(id: Int) = localDataSource.getCollage(id)

    override suspend fun updateCollage(collage: CollageData) = localDataSource.updateCollage(collage)

    override suspend fun deleteCollage(collage: CollageData) = localDataSource.deleteCollage(collage)
}
