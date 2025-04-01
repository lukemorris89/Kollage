package dev.rarebit.kollage.ui.createcollage

import dev.rarebit.core.viewmodel.BaseViewAction
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton

sealed class CreateCollageViewAction: BaseViewAction() {
    data class OnPrimaryToolButtonClicked(val button: CollageToolButton): CreateCollageViewAction()
    data class OnSecondaryToolButtonClicked(val button: CollageToolButton): CreateCollageViewAction()
}