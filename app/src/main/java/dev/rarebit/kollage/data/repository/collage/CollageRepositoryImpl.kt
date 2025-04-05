package dev.rarebit.kollage.data.repository.collage

import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import dev.rarebit.core.application.ApplicationContextProvider
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.IOException

class CollageRepositoryImpl(
    private val applicationContextProvider: ApplicationContextProvider,
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

    override fun updateFinalCollage(collage: ImageBitmap?) {
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

        uri
    }

}
