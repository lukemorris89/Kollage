package dev.rarebit.kollage.ui.permissions.data

import dev.rarebit.core.viewmodel.BaseViewEvent

sealed class PermissionsViewEvent : BaseViewEvent() {
    data object CheckPermissions : PermissionsViewEvent()
    data object RequestPermissions : PermissionsViewEvent()
    data object NavigateToCreateCollageScreen : PermissionsViewEvent()
    data object OpenAppSettings : PermissionsViewEvent()
}
