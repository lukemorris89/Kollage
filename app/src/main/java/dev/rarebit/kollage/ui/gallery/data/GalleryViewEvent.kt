package dev.rarebit.kollage.ui.gallery.data

import dev.rarebit.core.viewmodel.BaseViewEvent
import dev.rarebit.kollage.data.model.Collage

sealed class GalleryViewEvent : BaseViewEvent() {
    data object NavigateToTutorial : GalleryViewEvent()
    data object NavigateToNewCollage : GalleryViewEvent()
    data class NavigateToViewCollage(val collage: Collage) : GalleryViewEvent()
}
