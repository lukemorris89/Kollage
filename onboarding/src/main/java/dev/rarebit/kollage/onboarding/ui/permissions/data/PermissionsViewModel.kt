package dev.rarebit.kollage.onboarding.ui.permissions.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.onboarding.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PermissionsViewModel(
    override val resourceProvider: ResourceProvider,
) : BaseViewModel<PermissionsViewData, PermissionsViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        PermissionsViewData(
            title = R.string.permissions_title.asString,
            description = R.string.permissions_description.asString,
            ctaLabel = R.string.permissions_cta_label.asString,
            showPermissionRationaleBottomsheet = false,
            showPermissionDeniedBottomsheet = false,
            permissionRationaleDescription = R.string.permissions_rationale_description.asString,
            permissionRationalePrimaryCtaLabel = R.string.permissions_cta_label.asString,
            permissionRationaleSecondaryCtaLabel = R.string.permissions_dismiss.asString,
            permissionDeniedTitle = R.string.permissions_denied.asString,
            permissionDeniedDescription = R.string.permissions_denied_description.asString,
            permissionDeniedPrimaryCtaLabel = R.string.permissions_open_settings.asString,
            permissionDeniedSecondaryCtaLabel = R.string.permissions_dismiss.asString,
        )
    )
    override val viewData: StateFlow<PermissionsViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<PermissionsViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<PermissionsViewEvent>>
        get() = _viewEvent

    fun checkPermissions() {
        _viewEvent.tryEmit(PermissionsViewEvent.CheckPermissions)
    }

    fun requestPermissions() {
        _viewEvent.tryEmit(PermissionsViewEvent.RequestPermissions)
    }

    fun openAppSettings() {
        _viewEvent.tryEmit(PermissionsViewEvent.OpenAppSettings)
    }

    fun onPermissionGranted() {
        _viewEvent.tryEmit(PermissionsViewEvent.NavigateToCreateCollageScreen)
    }

    fun togglePermissionRationaleBottomsheet(isDisplayed: Boolean) {
        _viewData.update { currentState ->
            currentState.copy(
                showPermissionRationaleBottomsheet = isDisplayed
            )
        }
    }

    fun togglePermissionDeniedBottomsheet(isDisplayed: Boolean) {
        _viewData.update { currentState ->
            currentState.copy(
                showPermissionDeniedBottomsheet = isDisplayed
            )
        }
    }
}
