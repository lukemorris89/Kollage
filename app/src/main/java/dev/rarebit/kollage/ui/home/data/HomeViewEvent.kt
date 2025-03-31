package dev.rarebit.kollage.ui.home.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class HomeViewEvent : BaseViewEvent() {
    data object NavigateToNewCollage : HomeViewEvent()
}
