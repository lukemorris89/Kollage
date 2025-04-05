package dev.rarebit.kollage.ui.collageresult

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class CollageResultViewAction: BaseViewAction() {
    data object OnBackPressed: CollageResultViewAction()
}