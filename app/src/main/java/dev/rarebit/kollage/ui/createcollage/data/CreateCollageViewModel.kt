package dev.rarebit.kollage.ui.createcollage.data

import androidx.camera.core.CameraSelector
import androidx.compose.ui.graphics.Color
import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.data.repository.collage.CollageRepository
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.component.CollageTool
import dev.rarebit.kollage.ui.createcollage.component.secondarytools.CropShape
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateCollageViewModel(
    override val resourceProvider: ResourceProvider,
    private val collageRepository: CollageRepository,
) : BaseViewModel<CreateCollageViewData, CreateCollageViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        CreateCollageViewData(
            selectedPrimaryTool = null,
            selectedSecondaryTool = null,
            isToolbarExpanded = false,
            selectedCropShape = CropShape.RECTANGLE,
            showSecondaryToolOptions = false,
            defaultAlpha = 1f,
            selectedAlpha = 1f,
            selectedColor = Color.Transparent,
            cameraLensFacing = CameraSelector.LENS_FACING_BACK,
            hasBackCamera = true,
            hasFrontCamera = false,
            hasTorch = false,
            isTorchOn = false,
            undoEnabled = false,
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

    fun updateHasCameras(hasBackCamera: Boolean, hasFrontCamera: Boolean) {
        _viewData.update { currentState ->
            currentState.copy(
                hasBackCamera = hasBackCamera,
                hasFrontCamera = hasFrontCamera,
            )
        }
    }

    fun updateCameraLensFacing() {
        _viewData.update { currentState ->
            currentState.copy(
                cameraLensFacing = if (currentState.cameraLensFacing == CameraSelector.LENS_FACING_BACK && currentState.hasFrontCamera) {
                    CameraSelector.LENS_FACING_FRONT
                } else if (currentState.hasBackCamera) {
                    CameraSelector.LENS_FACING_BACK
                } else {
                    currentState.cameraLensFacing
                },
                selectedPrimaryTool = if (currentState.selectedPrimaryTool == CollageTool.SWITCH_CAMERA) {
                    null
                } else {
                    CollageTool.SWITCH_CAMERA
                },
                isToolbarExpanded = false,
            )
        }
    }

    fun updateHasTorch(hasTorch: Boolean) {
        _viewData.update { currentState ->
            currentState.copy(
                hasTorch = hasTorch
            )
        }
    }

    fun updateTorchOn() {
        _viewData.update { currentState ->
            currentState.copy(
                isTorchOn = !currentState.isTorchOn
            )
        }
    }

    fun toggleEdit() {
        _viewData.update { currentState ->
            currentState.copy(
                selectedPrimaryTool = if (currentState.selectedPrimaryTool == CollageTool.EDIT) {
                    null
                } else {
                    CollageTool.EDIT
                },
                isToolbarExpanded = !currentState.isToolbarExpanded,
                showSecondaryToolOptions = false,
            )
        }
    }

    fun toggleCropShape() {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = if (currentState.selectedSecondaryTool == CollageTool.SHAPE) {
                    null
                } else {
                    CollageTool.SHAPE
                },
                showSecondaryToolOptions = !(currentState.selectedSecondaryTool == CollageTool.SHAPE && currentState.showSecondaryToolOptions)
            )
        }
    }

    fun toggleAlpha() {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = if (currentState.selectedSecondaryTool == CollageTool.ALPHA) {
                    null
                } else {
                    CollageTool.ALPHA
                },
                showSecondaryToolOptions = !(currentState.selectedSecondaryTool == CollageTool.ALPHA && currentState.showSecondaryToolOptions)
            )
        }
    }

    fun toggleColour() {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = if (currentState.selectedSecondaryTool == CollageTool.COLOUR) {
                    null
                } else {
                    CollageTool.COLOUR
                },
                showSecondaryToolOptions = !(currentState.selectedSecondaryTool == CollageTool.COLOUR && currentState.showSecondaryToolOptions),
            )
        }
    }

    fun onCropShapeChanged(cropShape: CropShape) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = CollageTool.SHAPE,
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

    fun onColourChanged(colour: Color) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedColor = colour,
            )
        }
    }

    fun updateCollageLayer(collageLayer: CollageLayer) {
        _viewData.update { currentState ->
            currentState.copy(
                previousCollageLayer = if (currentState.previousCollageLayer == null) {
                    collageLayer
                } else {
                    currentState.currentCollageLayer
                },
                currentCollageLayer = collageLayer,
                undoEnabled = true,
            )
        }
        collageRepository.updateFinalCollage(null)
    }

    fun undoCollageLayer() {
        _viewData.update { currentState ->
            currentState.copy(
                currentCollageLayer = currentState.previousCollageLayer,
                previousCollageLayer = null,
                undoEnabled = false,
                selectedPrimaryTool = null,
                isToolbarExpanded = false,
            )
        }
        collageRepository.updateFinalCollage(null)
    }

    fun onDoneClicked() {
    }
}
