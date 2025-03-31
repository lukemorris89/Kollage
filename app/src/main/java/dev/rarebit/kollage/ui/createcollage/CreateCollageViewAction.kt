package dev.rarebit.kollage.ui.createcollage

import dev.rarebit.core.viewmodel.BaseViewAction
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton

sealed class CreateCollageViewAction: BaseViewAction() {
    data class OnCollageToolButtonClicked(val button: CollageToolButton): CreateCollageViewAction()
}