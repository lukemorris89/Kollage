package dev.rarebit.kollage.ui.createcollage.component.secondarytools

import androidx.annotation.DrawableRes
import dev.rarebit.design.R as DR

enum class CropShape(
    @DrawableRes val iconRes: Int,
) {
    RECTANGLE(iconRes = DR.drawable.ic_square),
    CIRCLE(iconRes = DR.drawable.ic_circle),
}
