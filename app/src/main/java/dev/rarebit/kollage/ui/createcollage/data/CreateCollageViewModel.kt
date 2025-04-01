package dev.rarebit.kollage.ui.createcollage.data

import androidx.compose.ui.graphics.Color
import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.data.repository.DataRepository
import dev.rarebit.kollage.ui.createcollage.component.CollageTool
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
            name = CollageTool.UNDO,
            onClick = {
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_camera_switch,
            hasSecondaryButtons = false,
            name = CollageTool.SWITCH_CAMERA,
            onClick = {
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_edit,
            hasSecondaryButtons = true,
            name = CollageTool.EDIT,
            onClick = {
            }
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_check,
            hasSecondaryButtons = false,
            name = CollageTool.DONE,
            onClick = {
            }
        ),
    )
    private val editSecondaryButtons = persistentListOf(
        CollageToolButton(
            iconRes = DR.drawable.ic_shape,
            name = CollageTool.SHAPE,
            onClick = {}
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_alpha,
            name = CollageTool.ALPHA,
            onClick = {}
        ),
        CollageToolButton(
            iconRes = DR.drawable.ic_filter,
            name = CollageTool.COLOUR,
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
            selectedAlpha = 1f,
            selectedColor = Color.Transparent,
        )
    )
    override val viewData: StateFlow<CreateCollageViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<CreateCollageViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<CreateCollageViewEvent>>
        get() = _viewEvent

    fun onBackPressed() {
        _viewEvent.tryEmit(CreateCollageViewEvent.NavigateBack)
    }

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
                showSecondaryToolOptions = if (currentState.selectedSecondaryTool == button) {
                    !currentState.showSecondaryToolOptions
                } else {
                    true
                },
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

    fun onAlphaChanged(alpha: Float) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedAlpha = alpha,
            )
        }
    }

    fun onColourSelected(colour: Color) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedColor = colour,
            )
        }
    }
}
