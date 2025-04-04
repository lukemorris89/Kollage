package dev.rarebit.kollage.ui.createcollage

import androidx.compose.ui.graphics.Color
import dev.rarebit.core.viewmodel.BaseViewAction
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShape

sealed class CreateCollageViewAction : BaseViewAction() {
    data object OnBackPressed : CreateCollageViewAction()
    data class OnCropShapeChanged(val shape: CropShape) : CreateCollageViewAction()
    data class OnAlphaChanged(val alpha: Float) : CreateCollageViewAction()
    data class OnColourChanged(val colour: Color) : CreateCollageViewAction()
    data class OnCamerasLoaded(val hasBackCamera: Boolean, val hasFrontCamera: Boolean) : CreateCollageViewAction()
    data class OnTorchDetected(val hasTorch: Boolean) : CreateCollageViewAction()
    data object OnTorchClicked : CreateCollageViewAction()
    data class OnCreateCollageLayer(val collageLayer: CollageLayer) : CreateCollageViewAction()
    data object OnUndoCollageLayer : CreateCollageViewAction()
    data object OnSwitchCamera : CreateCollageViewAction()
    data object OnEditClicked : CreateCollageViewAction()
    data object OnDoneClicked : CreateCollageViewAction()
    data object OnCropShapeClicked : CreateCollageViewAction()
    data object OnAlphaClicked : CreateCollageViewAction()
    data object OnColourClicked : CreateCollageViewAction()
}
