package dev.rarebit.kollage.onboarding.ui.permissions

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class PermissionsViewAction: BaseViewAction() {
    data object OnClickPrimaryCta: PermissionsViewAction()
}