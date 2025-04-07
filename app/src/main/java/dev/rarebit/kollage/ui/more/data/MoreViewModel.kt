package dev.rarebit.kollage.ui.more.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.R
import dev.rarebit.kollage.util.webview.constants.PRIVACY_POLICY_URL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MoreViewModel(
    override val resourceProvider: ResourceProvider,
) : BaseViewModel<MoreViewData, MoreViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        MoreViewData(
            title = R.string.more.asString,
            legalTitle = R.string.legal.asString,
            termsAndConditionsText = R.string.terms_and_conditions.asString,
            privacyPolicyText = R.string.privacy_policy.asString,
            createdByText = R.string.created_by_rarebit.asString,
            settingsTitle = R.string.app_settings.asString,
            reviewPermissionsText = R.string.review_permissions.asString,
            viewTutorialText = R.string.view_tutorial.asString,
        )
    )
    override val viewData: StateFlow<MoreViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<MoreViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<MoreViewEvent>>
        get() = _viewEvent

    fun onClickReviewPermissions() {
        _viewEvent.tryEmit(MoreViewEvent.OpenAppSettings)
    }

    fun onClickPrivacyPolicy() {
        _viewEvent.tryEmit(MoreViewEvent.OpenWebView(PRIVACY_POLICY_URL))
    }

    fun onClickViewTutorial() {
        _viewEvent.tryEmit(MoreViewEvent.NavigateToTutorial)
    }
}
