package dev.rarebit.kollage.onboarding.ui.permissions

import dev.rarebit.core.viewmodel.BaseViewAction

sealed class PermissionsViewAction : BaseViewAction() {
    data object OnClickPrimaryCta : PermissionsViewAction()
    data object OnClickPermissionRationalePrimaryCta : PermissionsViewAction()
    data object OnClickPermissionRationaleSecondaryCta : PermissionsViewAction()
    data class OnTogglePermissionRationaleBottomsheet(val isDisplayed: Boolean) :
        PermissionsViewAction()

    data object OnClickPermissionDeniedPrimaryCta : PermissionsViewAction()
    data object OnClickPermissionDeniedSecondaryCta : PermissionsViewAction()
    data class OnTogglePermissionDeniedBottomsheet(val isDisplayed: Boolean) :
        PermissionsViewAction()

    data object OnPermissionGranted : PermissionsViewAction()
}