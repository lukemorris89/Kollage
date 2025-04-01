package dev.rarebit.kollage.ui.createcollage.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.R
import dev.rarebit.kollage.data.repository.DataRepository
import dev.rarebit.kollage.ui.createcollage.component.CollageToolButton
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShape
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

    private val primaryButtons = persistentListOf(
        CollageToolButton(
            iconRes = DR.drawable.ic_undo,
            hasSecondaryButtons = false,
            onClick = {
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_camera_switch,
            hasSecondaryButtons = false,
            onClick = {
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_edit,
            hasSecondaryButtons = true,
            onClick = {
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_check,
            hasSecondaryButtons = false,
            onClick = {
            }
        ),
    )
    private val editSecondaryButtons = persistentListOf(
        CollageToolButton(
            iconRes = DR.drawable.ic_shape,
            onClick = {}
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_alpha,
            onClick = {}
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_filter,
            onClick = {}
        ),
    )

    private val _viewData = MutableStateFlow(
        CreateCollageViewData(
            primaryToolButtons = primaryButtons,
            secondaryToolButtons = editSecondaryButtons,
            selectedPrimaryTool = null,
            selectedSecondaryTool = null,
            isToolbarExpanded = false,
            selectedCropShape = CropShape.SQUARE,
            showSecondaryToolOptions = false,
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
                isToolbarExpanded = if (button == currentState.selectedPrimaryTool) {
                    !currentState.isToolbarExpanded && button.hasSecondaryButtons
                } else {
                    button.hasSecondaryButtons
                },
                selectedSecondaryTool = if (button != currentState.selectedPrimaryTool) {
                    null
                } else {
                    currentState.selectedSecondaryTool
                },
                showSecondaryToolOptions = false
            )
        }
    }

    fun onSecondaryToolButtonClicked(button: CollageToolButton) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = button,
                showSecondaryToolOptions = !currentState.showSecondaryToolOptions,
            )
        }
    }

    fun onCropShapeSelected(cropShape: CropShape) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedCropShape = cropShape,
            )
        }
    }
}
