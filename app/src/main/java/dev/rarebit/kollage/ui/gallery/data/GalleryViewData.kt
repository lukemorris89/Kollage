package dev.rarebit.kollage.ui.gallery.data

import dev.rarebit.core.viewmodel.BaseViewData

data class GalleryViewData(
    val title: String,
    val isEmptyGallery: Boolean,
    val primaryCtaLabel: String,
    val emptyDescription: String,
) : BaseViewData()
