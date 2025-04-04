package dev.rarebit.kollage.ui.createcollage.component

import androidx.annotation.DrawableRes

data class CollageToolButton(
    @DrawableRes val iconRes: Int,
    val name: CollageTool,
    val enabled: Boolean = true,
    val onClick: () -> Unit,
)
