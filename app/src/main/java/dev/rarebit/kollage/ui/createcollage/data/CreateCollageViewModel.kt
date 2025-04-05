package dev.rarebit.kollage.ui.createcollage.data

import androidx.camera.core.CameraSelector
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import dev.rarebit.core.application.ApplicationContextProvider
import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.design.component.tools.CollageTool
import dev.rarebit.design.component.tools.Tool
import dev.rarebit.kollage.data.repository.collage.CollageRepository
import dev.rarebit.kollage.ui.createcollage.collage.CollageLayer
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShape
import dev.rarebit.kollage.ui.createcollage.util.imageutil.drawKollageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateCollageViewModel(
    override val resourceProvider: ResourceProvider,
    private val applicationContextProvider: ApplicationContextProvider,
    private val collageRepository: CollageRepository,
) : BaseViewModel<CreateCollageViewData, CreateCollageViewEvent>(),
    WithResourceProvider {

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
                showFloatingToolRow = false,
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
                showFloatingToolRow = !(currentState.selectedSecondaryTool == CollageTool.SHAPE && currentState.showFloatingToolRow)
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
                showFloatingToolRow = !(currentState.selectedSecondaryTool == CollageTool.ALPHA && currentState.showFloatingToolRow)
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
                showFloatingToolRow = !(currentState.selectedSecondaryTool == CollageTool.COLOUR && currentState.showFloatingToolRow),
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
                isUndoEnabled = true,
            )
        }
        collageRepository.updateFinalCollage(null)
    }

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
        collageRepository.updateFinalCollage(null)
    }

    fun updateFinalCollage(finalCollage: ImageBitmap) {
        _viewData.update { currentState ->
            currentState.copy(
                isSaveLoading = true,
            )
        }
        val currentCollageLayer = _viewData.value.currentCollageLayer
        viewModelScope.launch(Dispatchers.IO) {
            val newKollage = drawKollageBitmap(
                applicationContextProvider(),
                finalCollage,
                currentCollageLayer,
            )
            with(collageRepository) {
                updateCollageBackground(finalCollage)
                updateFinalCollage(null)
                updateFinalCollage(newKollage)
            }
        }.invokeOnCompletion {
            _viewData.update { currentState ->
                currentState.copy(
                    isSaveLoading = false,
                )
            }
            _viewEvent.tryEmit(CreateCollageViewEvent.NavigateToCollageResultScreen)
        }
    }
}
