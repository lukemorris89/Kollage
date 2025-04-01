package dev.rarebit.kollage.ui.createcollage.data

import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShape
import kotlinx.collections.immutable.PersistentList

data class CreateCollageViewData(
    val primaryToolButtons: PersistentList<CollageToolButton>,
    val selectedPrimaryTool: CollageToolButton?,
    val secondaryToolButtons: PersistentList<CollageToolButton>,
    val selectedSecondaryTool: CollageToolButton?,
    val isToolbarExpanded: Boolean,
    val showSecondaryToolOptions: Boolean,
    val selectedCropShape: CropShape,
) : BaseViewData()
