package dev.rarebit.kollage.ui.createcollage.data

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageProxy
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import dev.rarebit.core.logger.Logger
import dev.rarebit.core.logger.WithLogger
import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.design.component.tools.CollageTool
import dev.rarebit.kollage.data.repository.collage.CollageRepository
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShape
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateCollageViewModel(
    override val resourceProvider: ResourceProvider,
    override val logger: Logger,
    private val collageRepository: CollageRepository,
) : BaseViewModel<CreateCollageViewData, CreateCollageViewEvent>(),
    WithResourceProvider,
    WithLogger {

    private val _viewData = MutableStateFlow(
        CreateCollageViewData(
            selectedPrimaryTool = null,
            selectedSecondaryTool = null,
            isToolbarExpanded = false,
            selectedCropShape = CropShape.RECTANGLE,
            showFloatingToolRow = false,
            defaultAlpha = 1f,
            selectedAlpha = 1f,
            selectedColor = Color.Transparent,
            cameraLensFacing = CameraSelector.LENS_FACING_BACK,
            hasBackCamera = true,
            hasFrontCamera = false,
            hasTorch = false,
            isTorchOn = false,
            isUndoEnabled = false,
            isSaveLoading = false,
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

    // Sets whether device has both cameras on camera init
    // Used to allow toggling cameras and setting initial camera for capture
    fun updateHasCameras(hasBackCamera: Boolean, hasFrontCamera: Boolean) {
        _viewData.update { currentState ->
            currentState.copy(
                hasBackCamera = hasBackCamera,
                hasFrontCamera = hasFrontCamera,
            )
        }
    }

    // Updates which camera (front or back) is currently active
    // Collapses expanded toolbar
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

    // Sets whether device has torch/flash on camera init
    fun updateHasTorch(hasTorch: Boolean) {
        _viewData.update { currentState ->
            currentState.copy(
                hasTorch = hasTorch
            )
        }
    }

    fun toggleTorch() {
        _viewData.update { currentState ->
            currentState.copy(
                isTorchOn = !currentState.isTorchOn
            )
        }
    }

    // Toggles whether edit tools are visible
    fun toggleEdit() {
        _viewData.update { currentState ->
            currentState.copy(
                selectedPrimaryTool = if (currentState.selectedPrimaryTool == CollageTool.EDIT) {
                    null
                } else {
                    CollageTool.EDIT
                },
                isToolbarExpanded = !currentState.isToolbarExpanded,
                showFloatingToolRow = if (!currentState.isToolbarExpanded) {
                    currentState.selectedSecondaryTool != null
                } else {
                    false
                },
            )
        }
    }

    // Toggles whether crop shape selection floating toolbar is visible
    fun toggleCropShape() {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = if (currentState.selectedSecondaryTool == CollageTool.SHAPE) {
                    null
                } else {
                    CollageTool.SHAPE
                },
                showFloatingToolRow = !(currentState.selectedSecondaryTool == CollageTool.SHAPE && currentState.showFloatingToolRow)
            )
        }
    }

    // Toggles whether alpha selection floating toolbar is visible
    fun toggleAlpha() {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = if (currentState.selectedSecondaryTool == CollageTool.ALPHA) {
                    null
                } else {
                    CollageTool.ALPHA
                },
                showFloatingToolRow = !(currentState.selectedSecondaryTool == CollageTool.ALPHA && currentState.showFloatingToolRow)
            )
        }
    }

    // Toggles whether layer colour filter selection floating toolbar is visible
    fun toggleColour() {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = if (currentState.selectedSecondaryTool == CollageTool.COLOUR) {
                    null
                } else {
                    CollageTool.COLOUR
                },
                showFloatingToolRow = !(currentState.selectedSecondaryTool == CollageTool.COLOUR && currentState.showFloatingToolRow),
            )
        }
    }

    // Changes shape of collage layer crop selection
    fun onCropShapeChanged(cropShape: CropShape) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedSecondaryTool = CollageTool.SHAPE,
                selectedCropShape = cropShape,
            )
        }
    }

    // Changes alpha of next collage layer
    fun onAlphaChanged(alpha: Float) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedAlpha = alpha,
            )
        }
    }

    // Changes colour filter of next collage layer (combined with colour filter)
    fun onColourChanged(colour: Color) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedColor = colour,
            )
        }
    }

    // Adds new collage layer on top of current collage and sets previous layer (used to undo)
    fun createNewCollageLayer(imageProxy: ImageProxy, rect: Rect) {
        val viewData = _viewData.value
        viewModelScope.launch {
            with(viewData) {
                collageRepository.updateCollage(
                    imageProxy = imageProxy,
                    rect = rect,
                    cameraLensFacing = cameraLensFacing,
                    currentCollageLayer = currentCollageLayer,
                    cropShape = selectedCropShape,
                    layerColour = selectedColor,
                    alpha = selectedAlpha,
                    onComplete = { collageLayer ->
                        _viewData.update { currentState ->
                            currentState.copy(
                                previousCollageLayer = if (currentState.previousCollageLayer == null) {
                                    collageLayer
                                } else {
                                    currentState.currentCollageLayer
                                },
                                currentCollageLayer = collageLayer,
                                isUndoEnabled = true,
                            )
                        }
                    }
                )
            }
        }
    }

    // Replaces current collage layer with previous layer (only possible once as only 2 layers are stored in memory)
    fun undoCollageLayer() {
        _viewData.update { currentState ->
            currentState.copy(
                currentCollageLayer = currentState.previousCollageLayer,
                previousCollageLayer = null,
                isUndoEnabled = false,
                selectedPrimaryTool = null,
                isToolbarExpanded = false,
            )
        }
    }

    // Flattens collage layers to one bitmap and adds the camera capture as a second layer underneath, then navigates
    fun saveFinalCollage(cameraCapture: ImageBitmap) {
        _viewData.update { it.copy(isSaveLoading = true) }

        viewModelScope.launch {
            try {
                with(collageRepository) {
                    updateFinalCollage(cameraCapture, _viewData.value.currentCollageLayer?.image)
                }
                _viewEvent.tryEmit(CreateCollageViewEvent.NavigateToCollageResultScreen)
            } catch (e: Exception) {
                logger.logError(e, tag = "CreateCollageViewModel") { "Error saving collage" }
                // TODO show error snackbar
            } finally {
                _viewData.update { it.copy(isSaveLoading = false) }
            }
        }
    }
}
