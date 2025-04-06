package dev.rarebit.kollage.ui.viewcollage.data

import dev.rarebit.core.viewmodel.BaseViewData
import dev.rarebit.kollage.data.model.Collage

data class ViewCollageViewData(
    val collage: Collage,
) : BaseViewData()