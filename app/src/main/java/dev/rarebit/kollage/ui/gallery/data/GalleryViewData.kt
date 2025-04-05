package dev.rarebit.kollage.ui.gallery.data

import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.kollage.data.database.model.CollageData
import kotlinx.collections.immutable.PersistentList

data class GalleryViewData(
    val title: String,
    val isEmptyGallery: Boolean,
    val primaryCtaLabel: String,
    val emptyDescription: String,
    val collageList: PersistentList<CollageData>,
) : BaseViewData()
