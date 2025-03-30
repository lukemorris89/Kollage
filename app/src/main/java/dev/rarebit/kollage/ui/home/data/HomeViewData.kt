package dev.rarebit.kollage.ui.home.data

import dev.rarebit.core.viewmodel.BaseViewData

data class HomeViewData(
    val selectedTab: NavigationItem = NavigationItem.Gallery,
): BaseViewData()