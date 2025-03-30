package dev.rarebit.kollage.ui.home

import dev.rarebit.core.viewmodel.BaseViewAction
import dev.rarebit.kollage.ui.home.data.NavigationItem

sealed class HomeViewAction: BaseViewAction() {
    data class OnClickBottomNavigationTab(val item: NavigationItem): HomeViewAction()
}