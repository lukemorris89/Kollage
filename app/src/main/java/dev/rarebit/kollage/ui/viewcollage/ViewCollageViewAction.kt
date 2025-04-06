package dev.rarebit.kollage.ui.viewcollage

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class ViewCollageViewAction: BaseViewAction() {
    data object OnBackPressed: ViewCollageViewAction()
}