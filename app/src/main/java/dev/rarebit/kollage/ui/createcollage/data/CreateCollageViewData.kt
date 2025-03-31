package dev.rarebit.kollage.ui.createcollage.data

import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton
import kotlinx.collections.immutable.PersistentList

data class CreateCollageViewData(
    val primaryButtons: PersistentList<CollageToolButton>,
    val selectedButton: CollageToolButton?,
): BaseViewData()