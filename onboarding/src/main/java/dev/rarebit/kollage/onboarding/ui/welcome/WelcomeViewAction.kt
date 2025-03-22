package dev.rarebit.kollage.onboarding.ui.welcome

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class WelcomeViewAction : BaseViewAction() {
    data object OnClickPrimaryCta : WelcomeViewAction()
}
