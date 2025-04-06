package dev.rarebit.kollage.ui.viewcollage.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class ViewCollageViewEvent: BaseViewEvent() {
    data object NavigateBack : ViewCollageViewEvent()

}