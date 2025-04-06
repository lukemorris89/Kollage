package dev.rarebit.kollage.ui.viewcollage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.rarebit.kollage.ui.viewcollage.data.ViewCollageViewEvent
import dev.rarebit.kollage.ui.viewcollage.data.ViewCollageViewModel

@Composable
fun ViewCollageScreen(
    viewModel: ViewCollageViewModel,
    navHostController: NavHostController
) {
    val viewData = viewModel.viewData.collectAsStateWithLifecycle()
    ViewCollageContent(
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
                ViewCollageViewEvent.NavigateBack -> {
                    navHostController.popBackStack()
                }
                else -> Unit
            }
        }
    }
}

context(ViewCollageViewModel)
private fun onViewAction(viewAction: ViewCollageViewAction) {
    when (viewAction) {
        ViewCollageViewAction.OnBackPressed -> onBackPressed()
        else -> {}
    }
}