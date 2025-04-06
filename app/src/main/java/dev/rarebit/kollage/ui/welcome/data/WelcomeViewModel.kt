package dev.rarebit.kollage.ui.welcome.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class WelcomeViewModel(
    override val resourceProvider: ResourceProvider,
) : BaseViewModel<WelcomeViewData, WelcomeViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        WelcomeViewData(
            title = R.string.welcome_app_name.asString,
            ctaLabel = R.string.welcome_begin.asString,
            termsText = R.string.welcome_terms.asString,
        )
    )
    override val viewData: StateFlow<WelcomeViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<WelcomeViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<WelcomeViewEvent>>
        get() = _viewEvent

    fun onPrimaryCtaClicked() {
        _viewEvent.tryEmit(WelcomeViewEvent.NavigateToHomeScreen)
    }
}
