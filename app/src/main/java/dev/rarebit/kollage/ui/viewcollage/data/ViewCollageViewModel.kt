package dev.rarebit.kollage.ui.viewcollage.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.data.model.Collage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ViewCollageViewModel(
    private val collage: Collage,
    override val resourceProvider: ResourceProvider,
) : BaseViewModel<ViewCollageViewData, ViewCollageViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        ViewCollageViewData(
            collage = collage,
        )
    )
    override val viewData: StateFlow<ViewCollageViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<ViewCollageViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<ViewCollageViewEvent>>
        get() = _viewEvent

    fun onBackPressed() {
        _viewEvent.tryEmit(ViewCollageViewEvent.NavigateBack)
    }
}