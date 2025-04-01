package dev.rarebit.kollage.ui.createcollage.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.data.repository.DataRepository
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import dev.rarebit.design.R as DR

class CreateCollageViewModel(
override val resourceProvider: ResourceProvider,
private val dataRepository: DataRepository,
) : BaseViewModel<CreateCollageViewData, CreateCollageViewEvent>(),
WithResourceProvider {

    private val previewPrimaryButtons = persistentListOf(
        CollageToolButton(
            iconRes = DR.drawable.ic_add,
            hasSecondaryButtons = true,
            onClick = {
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_menu,
            hasSecondaryButtons = true,
            onClick = {
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_gallery,
            hasSecondaryButtons = true,
            onClick = {
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_back,
            hasSecondaryButtons = true,
            onClick = {
            }
        ),
    )
    private val previewSecondaryButtons = persistentListOf(
        CollageToolButton(
            iconRes = DR.drawable.ic_add,
            onClick = {}
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_menu,
            onClick = {}
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_gallery,
            onClick = {}
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_back,
            onClick = {}
        ),
    )

    private val primaryButtonsList = persistentListOf<CollageToolButton>()
    private val _viewData = MutableStateFlow(
        CreateCollageViewData(
            primaryToolButtons = previewPrimaryButtons,
            secondaryToolButtons = previewSecondaryButtons,
            selectedPrimaryTool = null,
            selectedSecondaryTool = null,
            isToolbarExpanded = false,
        )
    )
    override val viewData: StateFlow<CreateCollageViewData>
    get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<CreateCollageViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<CreateCollageViewEvent>>
    get() = _viewEvent

    fun onPrimaryToolButtonClicked(button: CollageToolButton) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedPrimaryTool = button,
                isToolbarExpanded = button != currentState.selectedPrimaryTool && button.hasSecondaryButtons,
                selectedSecondaryTool = if (button != currentState.selectedPrimaryTool) {
                    null
                } else {
                    currentState.selectedSecondaryTool
                }
            )
        }
    }

    fun onSecondaryToolButtonClicked(button: CollageToolButton) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = button
            )
        }
    }
}