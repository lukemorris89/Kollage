package dev.rarebit.kollage.ui.createcollage

import dev.rarebit.core.viewmodel.BaseViewAction
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShape

sealed class CreateCollageViewAction: BaseViewAction() {
    data class OnPrimaryToolButtonClicked(val button: CollageToolButton): CreateCollageViewAction()
    data class OnSecondaryToolButtonClicked(val button: CollageToolButton): CreateCollageViewAction()
    data class OnCropShapeClicked(val shape: CropShape): CreateCollageViewAction()
}