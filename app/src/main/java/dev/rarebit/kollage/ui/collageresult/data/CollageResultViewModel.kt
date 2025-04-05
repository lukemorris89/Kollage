package dev.rarebit.kollage.ui.collageresult.data

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
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.LayerColour
import dev.rarebit.kollage.ui.createcollage.util.imageutil.BackgroundSelection
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat
import dev.rarebit.kollage.ui.createcollage.util.imageutil.generateImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollageResultViewModel(
    override val resourceProvider: ResourceProvider,
    override val logger: Logger,
    private val collageRepository: CollageRepository,
) : BaseViewModel<CollageResultViewData, CollageResultViewEvent>(),
    WithResourceProvider,
    WithLogger {

    private val _viewData = MutableStateFlow(
        CollageResultViewData(
            imageFormat = ImageFormat.PNG,
            backgroundSelection = BackgroundSelection.CAMERA,
            collage = collageRepository.finalCollage,
            backgroundBitmap = requireNotNull(collageRepository.collageBackground), // Camera capture always required
            backgroundColor = LayerColour.BLACK,
            selectedTool = null,
            showFloatingToolRow = false,
            isSaveLoading = false,
        )
    )
    override val viewData: StateFlow<CollageResultViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<CollageResultViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<CollageResultViewEvent>>
        get() = _viewEvent

    fun onBackPressed() {
        _viewEvent.tryEmit(CollageResultViewEvent.NavigateBack)
    }

    fun updateSelectedTool(tool: CollageTool) {
        _viewData.update { currentState ->
            currentState.copy(
                selectedTool = tool,
                showFloatingToolRow = if (tool == currentState.selectedTool) {
                    false
                } else {
                    tool != CollageTool.SAVE
                }
            )
        }
    }

    // Sets the background of the collage to either the camera capture or a flat colour
    fun updateBackgroundSelection(backgroundSelection: BackgroundSelection) {
        _viewData.update { currentState ->
            currentState.copy(
                backgroundSelection = backgroundSelection,
            )
        }
    }

    // Updates the background colour of the collage, visible if BackgroundSelection == COLOUR
    fun updateBackgroundColor(color: LayerColour) {
        _viewData.update { currentState ->
            currentState.copy(
                backgroundColor = color,
            )
        }
    }

    // Updates the output format of the image on saving
    fun updateImageFormat(imageFormat: ImageFormat) {
        _viewData.update { currentState ->
            currentState.copy(
                imageFormat = imageFormat,
            )
        }
    }

    // Flattens all layers and background for saving to local storage
    fun saveFinalBitmap() {
        val viewData = _viewData.value
        with(viewData) {
            _viewData.update { currentState ->
                currentState.copy(
                    isSaveLoading = true
                )
            }
            viewModelScope.launch {
                try {
                    val image = generateImage(
                        backgroundBitmap = backgroundBitmap,
                        backgroundColor = backgroundColor.colour,
                        backgroundSelection = backgroundSelection,
                        collage = collage,
                    )

                    collageRepository.saveCollageToLocalStorage(
                        bitmap = image,
                        imageFormat = _viewData.value.imageFormat,
                    )

                    _viewEvent.tryEmit(CollageResultViewEvent.NavigateToHomeScreen)
                } catch (e: Exception) {
                    logger.logError(
                        e,
                        tag = "CollageResultViewModel"
                    ) { "Error saving final collage" }
                    // TODO show error snackbar
                } finally {
                    _viewData.update { currentState ->
                        currentState.copy(
                            isSaveLoading = false
                        )
                    }
                }
            }
        }
    }

}