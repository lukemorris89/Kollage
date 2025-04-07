package dev.rarebit.kollage.ui.tutorial.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.R
import dev.rarebit.kollage.data.repository.DataRepository
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TutorialViewModel(
    private val isFromSettings: Boolean,
    override val resourceProvider: ResourceProvider,
    private val dataRepository: DataRepository,
) : BaseViewModel<TutorialViewData, TutorialViewEvent>(),
    WithResourceProvider {

    private val tutorialPages = persistentListOf<TutorialPage>(
        TutorialPage(
            index = 0,
            description = R.string.tutorial_step_1_description.asString,
        ),
        TutorialPage(
            index = 1,
            description = R.string.tutorial_step_2_description.asString,
        ),
        TutorialPage(
            index = 2,
            description = R.string.tutorial_step_3_description.asString,
        ),
        TutorialPage(
            index = 3,
            description = R.string.tutorial_step_4_description.asString,
        ),
    )

    private val _viewData = MutableStateFlow(
        TutorialViewData(
            currentPageIndex = 0,
            pages = tutorialPages,
            primaryCtaLabel = R.string.next.asString,
            backCtaLabel = R.string.back.asString
        )
    )
    override val viewData: StateFlow<TutorialViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<TutorialViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<TutorialViewEvent>>
        get() = _viewEvent

    fun onBackClicked() {
        if (_viewData.value.currentPageIndex == 0) {
            _viewEvent.tryEmit(TutorialViewEvent.NavigateBack)
        } else {
            _viewData.update { currentState ->
                currentState.copy(
                    currentPageIndex = currentState.currentPageIndex - 1,
                    primaryCtaLabel = R.string.next.asString
                )
            }
        }
    }

    fun onPrimaryCtaClicked() {
        val viewData = _viewData.value
        when {
            viewData.currentPageIndex < tutorialPages.size - 2 -> {
                _viewData.update { currentState ->
                    currentState.copy(
                        currentPageIndex = currentState.currentPageIndex + 1,
                        primaryCtaLabel = R.string.next.asString
                    )
                }
            }
            viewData.currentPageIndex == tutorialPages.size - 2 -> {
                _viewData.update { currentState ->
                    currentState.copy(
                        currentPageIndex = currentState.currentPageIndex + 1,
                        primaryCtaLabel = if (isFromSettings) {
                            R.string.done.asString
                        } else {
                            R.string.start.asString
                        },
                    )
                }
            }
            else -> {
                dataRepository.updateHasCompletedTutorial(true)
                if (isFromSettings) {
                    _viewEvent.tryEmit(TutorialViewEvent.NavigateBack)
                } else {
                    _viewEvent.tryEmit(TutorialViewEvent.NavigateToNewCollage)
                }
            }
        }
    }

    fun onPageClicked(pageIndex: Int) {
        _viewData.update { currentState ->
            currentState.copy(
                currentPageIndex = pageIndex,
                primaryCtaLabel = if (pageIndex == tutorialPages.size - 1) {
                    R.string.start.asString
                } else {
                    R.string.next.asString
                }
            )
        }
    }
}
