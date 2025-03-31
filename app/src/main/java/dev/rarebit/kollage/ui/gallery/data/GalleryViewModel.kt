package dev.rarebit.kollage.ui.gallery.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GalleryViewModel(
    override val resourceProvider: ResourceProvider,
) : BaseViewModel<GalleryViewData, GalleryViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        GalleryViewData(
            title = R.string.gallery.asString,
            isEmptyGallery = true,
            primaryCtaLabel = R.string.create_new.asString,
            emptyDescription = R.string.gallery_empty_description.asString,
        )
    )
    override val viewData: StateFlow<GalleryViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<GalleryViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<GalleryViewEvent>>
        get() = _viewEvent

    fun onClickAddNewCollage() {
        _viewEvent.tryEmit(GalleryViewEvent.NavigateToNewCollage)
    }
}