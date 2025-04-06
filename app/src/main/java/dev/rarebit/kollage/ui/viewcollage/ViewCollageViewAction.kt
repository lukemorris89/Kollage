package dev.rarebit.kollage.ui.viewcollage

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class ViewCollageViewAction : BaseViewAction() {
    data object OnBackPressed : ViewCollageViewAction()
    data object OnClickCollage : ViewCollageViewAction()
    data object OnClickMenu : ViewCollageViewAction()
    data object OnClickShare : ViewCollageViewAction()
    data object OnClickDelete : ViewCollageViewAction()
    data object OnConfirmDelete : ViewCollageViewAction()
    data object OnDismissDeleteDialog : ViewCollageViewAction()
}