package dev.rarebit.kollage.ui.collageresult.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.util.imageutil.BackgroundSelection
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat

data class CollageResultViewData(
    val imageFormat: ImageFormat = ImageFormat.PNG,
    val backgroundSelection: BackgroundSelection = BackgroundSelection.CAMERA,
    val collage: CollageLayer? = null,
    val backgroundBitmap: ImageBitmap,
    val backgroundColor: Color = Color.Black,
    val displayBitmap: ImageBitmap? = null,
    val showColorPicker: Boolean = false,
    val galleryBitmap: ImageBitmap? = null,
    val isLoading: Boolean = false,
    val isCollageSaved: Boolean = false,
    val finalBitmap: ImageBitmap? = null,
): BaseViewData()