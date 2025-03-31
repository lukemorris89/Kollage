package dev.rarebit.kollage.onboarding.ui.tutorial.data

import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ViewEvent
import dev.rarebit.core.view.WithResourceProvider
import dev.rarebit.core.viewmodel.BaseViewModel
import dev.rarebit.core.viewmodel.tryEmit
import dev.rarebit.core.viewmodel.viewEventFlow
import dev.rarebit.kollage.onboarding.R
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TutorialViewModel(
    override val resourceProvider: ResourceProvider,
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
            skipCtaLabel = R.string.skip.asString
        )
    )
    override val viewData: StateFlow<TutorialViewData>
        get() = _viewData.asStateFlow()

    private val _viewEvent = viewEventFlow<TutorialViewEvent>()
    override val viewEvent: SharedFlow<ViewEvent<TutorialViewEvent>>
        get() = _viewEvent

    fun onBackClicked() {
        _viewEvent.tryEmit(TutorialViewEvent.NavigateBack)
    }

    fun onPrimaryCtaClicked() {
        val viewData = _viewData.value
        when {
            viewData.currentPageIndex < tutorialPages.size - 1 -> {
                _viewData.update { currentState ->
                    currentState.copy(
                        currentPageIndex = currentState.currentPageIndex + 1,
                        showSkip = true,
                        primaryCtaLabel = R.string.next.asString
                    )
                }
            }
            viewData.currentPageIndex == tutorialPages.size - 1 -> {
                _viewData.update { currentState ->
                    currentState.copy(
                        showSkip = false,
                        primaryCtaLabel = R.string.start.asString,
                    )
                }
            }
            else -> {
                _viewEvent.tryEmit(TutorialViewEvent.NavigateToNewCollage)
            }
        }
    }

    fun onPageClicked(pageIndex: Int) {
        _viewData.update { currentState ->
            currentState.copy(
                currentPageIndex = pageIndex,
                showSkip = currentState.currentPageIndex < tutorialPages.size - 1
            )
        }
    }

    fun onSkipClicked() {
        _viewEvent.tryEmit(TutorialViewEvent.NavigateToNewCollage)
    }
}
