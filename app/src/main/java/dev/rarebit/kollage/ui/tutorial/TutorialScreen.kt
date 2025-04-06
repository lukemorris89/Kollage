package dev.rarebit.kollage.ui.tutorial

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import dev.rarebit.core.permission.hasCameraPermission
import dev.rarebit.kollage.navigation.AppRoute
import dev.rarebit.kollage.ui.tutorial.data.TutorialViewEvent
import dev.rarebit.kollage.ui.tutorial.data.TutorialViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TutorialScreen(
    navHostController: NavHostController,
    viewModel: TutorialViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val viewData = viewModel.viewData.collectAsStateWithLifecycle()
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
                TutorialViewEvent.NavigateToNewCollage -> {
                    if (context.hasCameraPermission()) {
                        val navOptions = NavOptions.Builder().apply {
                            setPopUpTo<AppRoute.Home>(inclusive = false)
                        }.build()
                        navHostController.navigate(AppRoute.CreateCollage, navOptions)
                    } else {
                        navHostController.navigate(AppRoute.Permissions)
                    }
                }
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
    }
}
