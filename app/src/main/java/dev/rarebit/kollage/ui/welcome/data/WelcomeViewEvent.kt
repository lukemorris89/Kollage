package dev.rarebit.kollage.ui.welcome.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class WelcomeViewEvent : BaseViewEvent() {
    data object NavigateToHomeScreen : WelcomeViewEvent()
}
