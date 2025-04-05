package dev.rarebit.kollage.ui.createcollage.data

import androidx.compose.ui.graphics.Color
import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.design.component.tools.Tool
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShape

data class CreateCollageViewData(
    val currentCollageLayer: CollageLayer? = null,
    val previousCollageLayer: CollageLayer? = null,
    val selectedPrimaryTool: Tool?,
    val selectedSecondaryTool: Tool?,
    val isToolbarExpanded: Boolean,
    val showFloatingToolRow: Boolean,
    val selectedCropShape: CropShape,
    val defaultAlpha: Float,
    val selectedAlpha: Float,
    val selectedColor: Color,
    val cameraLensFacing: Int,
    val hasBackCamera: Boolean,
    val hasFrontCamera: Boolean,
    val hasTorch: Boolean,
    val isTorchOn: Boolean,
    val isUndoEnabled: Boolean,
    val isSaveLoading: Boolean,
) : BaseViewData()
