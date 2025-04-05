package dev.rarebit.kollage.ui.collageresult.data

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.viewModelScope
import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.data.repository.collage.CollageRepository
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.LayerColour
import dev.rarebit.kollage.ui.createcollage.util.imageutil.BackgroundSelection
import dev.rarebit.kollage.ui.createcollage.util.imageutil.ImageFormat
import dev.rarebit.kollage.ui.createcollage.util.imageutil.generateImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollageResultViewModel(
    override val resourceProvider: ResourceProvider,
    private val collageRepository: CollageRepository,
) : BaseViewModel<CollageResultViewData, CollageResultViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        CollageResultViewData(
            imageFormat = ImageFormat.PNG,
            backgroundSelection = BackgroundSelection.CAMERA,
            collage = null,
            backgroundBitmap = collageRepository.collageBackground,
            backgroundColor = LayerColour.BLACK,
            displayBitmap = null,
            isSaveLoading = false,
            isCollageSaved = false,
            finalBitmap = null,
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

    fun updateBackgroundSelection(backgroundSelection: BackgroundSelection) {
        _viewData.update { currentState ->
            currentState.copy(
                backgroundSelection = backgroundSelection,
                isCollageSaved = false,
            )
        }
    }

    fun updateBackgroundColor(color: LayerColour) {
        _viewData.update { currentState ->
            currentState.copy(
                backgroundColor = color,
                isCollageSaved = false,
            )
        }
    }

    fun updateImageFormat(imageFormat: ImageFormat) {
        _viewData.update { currentState ->
            currentState.copy(
                imageFormat = imageFormat,
                isCollageSaved = false,
            )
        }
    }

    private fun generateFinalBitmap(): ImageBitmap? {
        val viewData = _viewData.value
        with(viewData) {
            if (backgroundBitmap == null) return null

            val image = generateImage(
                context = applicationContextProvider(),
                backgroundBitmap = backgroundBitmap,
                backgroundColor = backgroundColor.colour,
                backgroundSelection = backgroundSelection,
                collage = collage,
            )

            _viewData.update { currentState ->
                currentState.copy(
                    finalBitmap = image
                )
            }
            return image
        }
    }

    suspend fun saveImage() {
        _viewData.update { currentState ->
            currentState.copy(
                isSaveLoading = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {

        }
        val image = generateFinalBitmap()
        if (image != null) {
            val uri = saveImageToLocalStorage(
                context = applicationContextProvider(),
                bitmap = image,
                imageFormat = _uiState.value.imageFormat,
            )
            saveKollage(uri.toString())
        } else {
            //TODO Handle generate image failure
        }
    }
}