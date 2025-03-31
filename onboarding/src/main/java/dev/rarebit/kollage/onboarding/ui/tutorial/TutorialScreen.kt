package dev.rarebit.kollage.onboarding.ui.tutorial

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import dev.rarebit.kollage.onboarding.ui.tutorial.data.TutorialViewEvent
import dev.rarebit.kollage.onboarding.ui.tutorial.data.TutorialViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TutorialScreen(
    navHostController: NavHostController,
    viewModel: TutorialViewModel = koinViewModel()
) {
    val viewData = viewModel.viewData.collectAsState()
    TutorialContent(
        viewData = viewData.value,
        onViewAction = {
            with(viewModel) {
                onViewAction(it)
            }
        }
    )

    LaunchedEffect(true) {
        viewModel.viewEvent.collect { event ->
            when (event.consume()) {
                TutorialViewEvent.NavigateBack -> navHostController.popBackStack()
                TutorialViewEvent.NavigateToNewCollage -> TODO()
                null -> Unit
            }
        }
    }
}

context(TutorialViewModel)
private fun onViewAction(viewAction: TutorialViewAction) {
    when (viewAction) {
        TutorialViewAction.OnClickBack -> onBackClicked()
        TutorialViewAction.OnClickPrimaryCta -> onPrimaryCtaClicked()
        is TutorialViewAction.OnClickPage -> onPageClicked(viewAction.pageIndex)
        TutorialViewAction.OnClickSkipCta -> onSkipClicked()
    }
}
