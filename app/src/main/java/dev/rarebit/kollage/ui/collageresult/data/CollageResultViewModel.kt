package dev.rarebit.kollage.ui.collageresult.data

import dev.rarebit.core.application.ApplicationContextProvider
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CollageResultViewModel(
    override val resourceProvider: ResourceProvider,
    private val applicationContextProvider: ApplicationContextProvider,
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
}