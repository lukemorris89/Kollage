package dev.rarebit.kollage.ui.gallery.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class GalleryViewEvent : BaseViewEvent() {
    data object NavigateToTutorial : GalleryViewEvent()
    data object NavigateToNewCollage : GalleryViewEvent()
}
