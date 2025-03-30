package dev.rarebit.kollage.ui.home.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    override val resourceProvider: ResourceProvider,
) : BaseViewModel<HomeViewData, HomeViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        HomeViewData(
            selectedTab = NavigationItem.Gallery,
        )
    )
    override val viewData: StateFlow<HomeViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<HomeViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<HomeViewEvent>>
        get() = _viewEvent

    fun onClickBottomNavigationTab(item: NavigationItem) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedTab = item,
            )
        }
    }

    fun onClickAddNewCollage() {
        _viewEvent.tryEmit(HomeViewEvent.NavigateToNewCollage)
    }
}
