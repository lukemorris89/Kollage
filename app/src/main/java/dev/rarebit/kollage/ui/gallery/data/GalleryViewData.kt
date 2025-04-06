package dev.rarebit.kollage.ui.gallery.data

import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.kollage.data.model.Collage
import kotlinx.collections.immutable.PersistentList

data class GalleryViewData(
    val title: String,
    val isEmptyGallery: Boolean,
    val primaryCtaLabel: String,
    val emptyDescription: String,
    val collageList: PersistentList<CollageDayGroup>,
) : BaseViewData() {
    data class CollageDayGroup(
        val date: String,
        val collages: List<Collage>
    )
}
