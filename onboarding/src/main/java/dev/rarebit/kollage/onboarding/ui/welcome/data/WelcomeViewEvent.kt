package dev.rarebit.kollage.onboarding.ui.welcome.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class WelcomeViewEvent : BaseViewEvent() {
    data object CheckPermissions : WelcomeViewEvent()
}
