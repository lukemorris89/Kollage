package dev.rarebit.kollage.ui.createcollage

import androidx.compose.ui.graphics.Color
import dev.rarebit.core.viewmodel.BaseViewAction
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShape

sealed class CreateCollageViewAction : BaseViewAction() {
    data object OnBackPressed: CreateCollageViewAction()
    data class OnPrimaryToolButtonClicked(val button: CollageToolButton) : CreateCollageViewAction()
    data class OnSecondaryToolButtonClicked(val button: CollageToolButton) : CreateCollageViewAction()
    data class OnCropShapeClicked(val shape: CropShape) : CreateCollageViewAction()
    data class OnAlphaChanged(val alpha: Float) : CreateCollageViewAction()
    data class OnColourClicked(val colour: Color) : CreateCollageViewAction()
    data class OnCamerasLoaded(val hasBackCamera: Boolean, val hasFrontCamera: Boolean) : CreateCollageViewAction()
    data object OnTorchClicked: CreateCollageViewAction()
}
