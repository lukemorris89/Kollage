package dev.rarebit.kollage.ui.viewcollage.data

import androidx.lifecycle.viewModelScope
import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.R
import dev.rarebit.kollage.data.model.Collage
import dev.rarebit.kollage.data.repository.collage.CollageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewCollageViewModel(
    private val collage: Collage,
    override val resourceProvider: ResourceProvider,
    private val collageRepository: CollageRepository,
) : BaseViewModel<ViewCollageViewData, ViewCollageViewEvent>(),
    WithResourceProvider {

    private val _viewData = MutableStateFlow(
        ViewCollageViewData(
            showTopAppBar = true,
            showMenuDropdown = false,
            collage = collage,
            showDeleteDialog = false,
            deleteDialogTitle = R.string.delete_collage.asString,
            deleteDialogDescription = R.string.are_you_sure_you_want_to_delete_this_collage.asString,
        )
    )

    override val viewData: StateFlow<ViewCollageViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<ViewCollageViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<ViewCollageViewEvent>>
        get() = _viewEvent

    fun onBackPressed() {
        _viewEvent.tryEmit(ViewCollageViewEvent.NavigateBack)
    }

    fun toggleTopAppBar() {
        _viewData.update { currentState ->
            currentState.copy(
                showTopAppBar = !currentState.showTopAppBar
            )
        }
    }

    fun toggleMenu() {
        _viewData.update { currentState ->
            currentState.copy(
                showMenuDropdown = !currentState.showMenuDropdown
            )
        }
    }

    fun updateShowDeleteDialog(show: Boolean) {
        _viewData.update { currentState ->
            currentState.copy(
                showMenuDropdown = false,
                showDeleteDialog = show
            )
        }
    }

    fun deleteCollage() {
        viewModelScope.launch {
            collageRepository.deleteCollage(_viewData.value.collage)
        }
        updateShowDeleteDialog(false)
        _viewEvent.tryEmit(ViewCollageViewEvent.NavigateBack)
    }

    fun showShareSheet() {
        _viewEvent.tryEmit(ViewCollageViewEvent.ShowShareSheet(_viewData.value.collage.uri))
    }
}
