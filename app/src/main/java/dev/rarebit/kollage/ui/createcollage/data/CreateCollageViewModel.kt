package dev.rarebit.kollage.ui.createcollage.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.R
import dev.rarebit.kollage.data.repository.DataRepository
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton
import dev.rarebit.kollage.ui.gallery.data.GalleryViewData
import dev.rarebit.kollage.ui.gallery.data.GalleryViewEvent
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateCollageViewModel(
override val resourceProvider: ResourceProvider,
private val dataRepository: DataRepository,
) : BaseViewModel<CreateCollageViewData, CreateCollageViewEvent>(),
WithResourceProvider {

    private val primaryButtonsList = persistentListOf<CollageToolButton>()
    private val _viewData = MutableStateFlow(
        CreateCollageViewData(
            primaryButtons = primaryButtonsList,
            selectedButton = null,
        )
    )
    override val viewData: StateFlow<CreateCollageViewData>
    get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<CreateCollageViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<CreateCollageViewEvent>>
    get() = _viewEvent

    fun onCollageToolButtonClicked(button: CollageToolButton) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedButton = if (currentState.selectedButton != button) {
                    button
                } else {
                    null
                }
            )
        }
    }
}