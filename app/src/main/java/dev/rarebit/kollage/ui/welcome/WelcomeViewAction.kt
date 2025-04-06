package dev.rarebit.kollage.ui.welcome

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class WelcomeViewAction : BaseViewAction() {
    data object OnClickPrimaryCta : WelcomeViewAction()
}
