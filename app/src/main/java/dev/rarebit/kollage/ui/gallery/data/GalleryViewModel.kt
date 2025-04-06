package dev.rarebit.kollage.ui.gallery.data

import androidx.lifecycle.viewModelScope
import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.R
import dev.rarebit.kollage.data.repository.DataRepository
import dev.rarebit.kollage.data.repository.collage.CollageRepository
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GalleryViewModel(
    override val resourceProvider: ResourceProvider,
    private val dataRepository: DataRepository,
    private val collageRepository: CollageRepository,
) : BaseViewModel<GalleryViewData, GalleryViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        GalleryViewData(
            title = R.string.gallery.asString,
            isEmptyGallery = true,
            primaryCtaLabel = R.string.create_new.asString,
            emptyDescription = R.string.gallery_empty_description.asString,
            collageList = persistentListOf()
        )
    )
    override val viewData: StateFlow<GalleryViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<GalleryViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<GalleryViewEvent>>
        get() = _viewEvent

    init {
        viewModelScope.launch {
            collageRepository.getAllCollages().collect {
                _viewData.value = _viewData.value.copy(
                    collageList = it.toPersistentList(),
                    isEmptyGallery = it.isEmpty()
                )
            }
        }
    }

    fun onClickAddNewCollage() {
        if (dataRepository.getHasCompletedTutorial()) {
            _viewEvent.tryEmit(GalleryViewEvent.NavigateToNewCollage)
        } else {
            _viewEvent.tryEmit(GalleryViewEvent.NavigateToTutorial)
        }
    }
}
