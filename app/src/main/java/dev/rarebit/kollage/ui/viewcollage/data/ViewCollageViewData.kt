package dev.rarebit.kollage.ui.viewcollage.data

import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.kollage.data.model.Collage

data class ViewCollageViewData(
    val showTopAppBar: Boolean,
    val showMenuDropdown: Boolean,
    val collage: Collage,
    val showDeleteDialog: Boolean,
    val deleteDialogTitle: String,
    val deleteDialogDescription: String,
) : BaseViewData()
