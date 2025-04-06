package dev.rarebit.kollage.ui.viewcollage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.rarebit.kollage.ui.viewcollage.data.ViewCollageViewEvent
import dev.rarebit.kollage.ui.viewcollage.data.ViewCollageViewModel
import dev.rarebit.kollage.util.ShareManager

@Composable
fun ViewCollageScreen(
    viewModel: ViewCollageViewModel,
    navHostController: NavHostController
) {
    val context = LocalContext.current
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
            when (val consumedEvent = event.consume()) {
                ViewCollageViewEvent.NavigateBack -> {
                    navHostController.popBackStack()
                }

                is ViewCollageViewEvent.ShowShareSheet -> {
                    ShareManager.shareCollage(context, consumedEvent.uri)
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
        ViewCollageViewAction.OnClickCollage -> toggleTopAppBar()
        ViewCollageViewAction.OnClickMenu -> toggleMenu()
        ViewCollageViewAction.OnClickDelete -> updateShowDeleteDialog(true)
        ViewCollageViewAction.OnClickShare -> showShareSheet()
        ViewCollageViewAction.OnConfirmDelete -> deleteCollage()
        ViewCollageViewAction.OnDismissDeleteDialog -> updateShowDeleteDialog(false)
    }
}
