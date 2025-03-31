package dev.rarebit.kollage.ui.home.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class HomeViewEvent : BaseViewEvent() {
    data object NavigateBack : HomeViewEvent()
    data object NavigateToTutorial : HomeViewEvent()
    data object NavigateToNewCollage : HomeViewEvent()
}
