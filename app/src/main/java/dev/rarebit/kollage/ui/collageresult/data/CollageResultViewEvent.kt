package dev.rarebit.kollage.ui.collageresult.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class CollageResultViewEvent: BaseViewEvent() {
    data object NavigateBack : CollageResultViewEvent()
    data object NavigateToHomeScreen : CollageResultViewEvent()
}