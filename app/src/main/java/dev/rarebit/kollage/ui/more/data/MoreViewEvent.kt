package dev.rarebit.kollage.ui.more.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class MoreViewEvent : BaseViewEvent() {
    data object OpenAppSettings : MoreViewEvent()
}
