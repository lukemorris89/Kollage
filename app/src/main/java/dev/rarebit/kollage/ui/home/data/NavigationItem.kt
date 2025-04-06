package dev.rarebit.kollage.ui.home.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import dev.rarebit.kollage.R
import dev.rarebit.design.R as DR

enum class NavigationItem(
    @StringRes val displayNameRes: Int,
    @DrawableRes val iconRes: Int,
) {
    Gallery(
        displayNameRes = R.string.gallery,
        iconRes = DR.drawable.ic_gallery,
    ),
    More(
        displayNameRes = R.string.more,
        iconRes = DR.drawable.ic_menu,
    ),
}
