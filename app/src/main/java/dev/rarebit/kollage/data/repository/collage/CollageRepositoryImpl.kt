package dev.rarebit.kollage.data.repository.collage

import androidx.compose.ui.graphics.ImageBitmap
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CollageRepositoryImpl : CollageRepository {
    private val _collage = MutableStateFlow<CollageLayer?>(null)
    override val collage = _collage.asStateFlow()

    private val _previousCollage = MutableStateFlow<CollageLayer?>(null)
    override val previousCollage = _previousCollage.asStateFlow()

    private val _collageBackground = MutableStateFlow<ImageBitmap?>(null)
    override val collageBackground = _collageBackground.asStateFlow()

    private val _finalCollage = MutableStateFlow<ImageBitmap?>(null)
    override val finalCollage = _finalCollage.asStateFlow()

    private val _imageFormat = MutableStateFlow(ImageFormat.PNG)
    override val imageFormat = _imageFormat.asStateFlow()

    override fun updateImageFormat(imageFormat: ImageFormat) {
        _imageFormat.value = imageFormat
    }

    override fun updatePreviousCollageLayer(collage: CollageLayer?) {
        _previousCollage.value = collage
    }

    override fun updateCollageLayer(collage: CollageLayer?) {
        _collage.value = collage
    }

    override fun updateCollageBackground(collageBackground: ImageBitmap?) {
        _collageBackground.value = collageBackground
    }

    override fun updateFinalCollage(finalCollage: ImageBitmap?) {
        _finalCollage.value = finalCollage
    }

    override fun clearImage() {
        _previousCollage.value = null
        _collage.value = null
        _collageBackground.value = null
    }
}
