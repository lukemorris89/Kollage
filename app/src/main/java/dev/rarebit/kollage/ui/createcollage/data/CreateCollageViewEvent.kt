package dev.rarebit.kollage.ui.createcollage.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class CreateCollageViewEvent : BaseViewEvent() {
    data object NavigateBack: CreateCollageViewEvent()
}
