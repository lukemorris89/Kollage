package dev.rarebit.kollage.ui.createcollage

import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import dev.rarebit.core.viewmodel.BaseViewAction
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShape

sealed class CreateCollageViewAction : BaseViewAction() {
    data object OnBackPressed : CreateCollageViewAction()
    data class OnCropShapeChanged(val shape: CropShape) : CreateCollageViewAction()
    data class OnColourChanged(val colour: Color) : CreateCollageViewAction()
    data class OnColourAlphaChanged(val alpha: Float) : CreateCollageViewAction()
    data class OnCamerasLoaded(val hasBackCamera: Boolean, val hasFrontCamera: Boolean) : CreateCollageViewAction()
    data class OnTorchDetected(val hasTorch: Boolean) : CreateCollageViewAction()
    data object OnTorchClicked : CreateCollageViewAction()
    data class OnCreateCollageLayer(val imageProxy: ImageProxy, val cropRect: Rect) : CreateCollageViewAction()
    data object OnUndoCollageLayer : CreateCollageViewAction()
    data object OnSwitchCamera : CreateCollageViewAction()
    data object OnEditClicked : CreateCollageViewAction()
    data class OnDoneClicked(val cameraCapture: ImageBitmap) : CreateCollageViewAction()
    data object OnCropShapeClicked : CreateCollageViewAction()
    data object OnAlphaClicked : CreateCollageViewAction()
    data object OnColourClicked : CreateCollageViewAction()
    data object OnConfirmExit : CreateCollageViewAction()
    data object OnDismissConfirmExitDialog : CreateCollageViewAction()
}
