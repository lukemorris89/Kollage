package dev.rarebit.kollage.ui.gallery

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class GalleryViewAction : BaseViewAction() {
    data object OnClickCreateNew : GalleryViewAction()
}
