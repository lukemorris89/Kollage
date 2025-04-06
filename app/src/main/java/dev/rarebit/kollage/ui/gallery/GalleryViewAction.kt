package dev.rarebit.kollage.ui.gallery

import dev.rarebit.core.viewmodel.BaseViewAction
import dev.rarebit.kollage.data.model.Collage

sealed class GalleryViewAction : BaseViewAction() {
    data object OnClickCreateNew : GalleryViewAction()
    data class OnClickThumbnail(val collage: Collage) : GalleryViewAction()
    data class OnClickThumbnailSelectMode(val collage: Collage) : GalleryViewAction()
    data object OnLongClickThumbnail : GalleryViewAction()
    data object OnClickSelect : GalleryViewAction()
    data object OnClickDelete : GalleryViewAction()
    data object OnDismissConfirmDeleteDialog : GalleryViewAction()
    data object OnConfirmDelete : GalleryViewAction()
}
