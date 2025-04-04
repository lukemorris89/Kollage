package dev.rarebit.kollage.ui.createcollage.data

import androidx.compose.ui.graphics.Color
import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShape
import kotlinx.collections.immutable.PersistentList

data class CreateCollageViewData(
    val currentCollageLayer: CollageLayer? = null,
    val previousCollageLayer: CollageLayer? = null,
    val primaryToolButtons: PersistentList<CollageToolButton>,
    val selectedPrimaryTool: CollageToolButton?,
    val secondaryToolButtons: PersistentList<CollageToolButton>,
    val selectedSecondaryTool: CollageToolButton?,
    val isToolbarExpanded: Boolean,
    val showSecondaryToolOptions: Boolean,
    val selectedCropShape: CropShape,
    val defaultAlpha: Float,
    val selectedAlpha: Float,
    val selectedColor: Color,
    val cameraLensFacing: Int,
    val hasBackCamera: Boolean,
    val hasFrontCamera: Boolean,
    val hasTorch: Boolean,
    val isTorchOn: Boolean,
    val undoEnabled: Boolean,
) : BaseViewData()
