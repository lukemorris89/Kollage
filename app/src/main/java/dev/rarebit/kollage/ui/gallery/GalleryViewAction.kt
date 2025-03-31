package dev.rarebit.kollage.ui.gallery

sealed class GalleryViewAction() {
    data object OnClickCreateNew : GalleryViewAction()
}
