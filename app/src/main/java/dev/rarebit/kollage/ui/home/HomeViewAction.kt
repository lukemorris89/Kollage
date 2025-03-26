package dev.rarebit.kollage.ui.home

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class HomeViewAction: BaseViewAction() {
    data class OnClickBottomNavigationTab(val index: Int): HomeViewAction()
}