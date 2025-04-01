package dev.rarebit.kollage.ui.createcollage.component

import androidx.annotation.DrawableRes

data class CollageToolButton(
    @DrawableRes val iconRes: Int,
    val hasSecondaryButtons: Boolean = false,
    val onClick: () -> Unit,
)