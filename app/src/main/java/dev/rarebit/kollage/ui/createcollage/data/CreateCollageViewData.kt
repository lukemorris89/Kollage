package dev.rarebit.kollage.ui.createcollage.data

import androidx.compose.ui.graphics.Color
import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.design.component.tools.CollageTool
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShape

data class CreateCollageViewData(
    val currentCollageLayer: CollageLayer? = null,
    val previousCollageLayer: CollageLayer? = null,
    val selectedPrimaryTool: CollageTool?,
    val selectedSecondaryTool: CollageTool?,
    val isToolbarExpanded: Boolean,
    val showFloatingToolRow: Boolean,
    val selectedCropShape: CropShape,
    val selectedFilterColour: Color,
    val selectedFilterColourAlpha: Float,
    val cameraLensFacing: Int,
    val hasBackCamera: Boolean,
    val hasFrontCamera: Boolean,
    val hasTorch: Boolean,
    val isTorchOn: Boolean,
    val isUndoEnabled: Boolean,
    val isSaveLoading: Boolean,
    val showConfirmExitDialog: Boolean,
    val exitDialogTitle: String,
    val exitDialogDescription: String,
) : BaseViewData()
