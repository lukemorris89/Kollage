package dev.rarebit.kollage.ui.collageresult.data

import androidx.compose.ui.graphics.ImageBitmap
import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.LayerColour
import dev.rarebit.kollage.ui.createcollage.util.imageutil.BackgroundSelection
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat

data class CollageResultViewData(
    val imageFormat: ImageFormat,
    val backgroundSelection: BackgroundSelection,
    val collage: ImageBitmap?,
    val backgroundBitmap: ImageBitmap,
    val backgroundColor: LayerColour,
    val isSaveLoading: Boolean,
): BaseViewData()