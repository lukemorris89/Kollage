package dev.rarebit.kollage.ui.more

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import dev.rarebit.core.permission.openAppSettings
import dev.rarebit.kollage.ui.more.data.MoreViewEvent
import dev.rarebit.kollage.ui.more.data.MoreViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoreScreen(
    navHostController: NavHostController,
    viewModel: MoreViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val viewData by viewModel.viewData.collectAsState()
    MoreContent(
        viewData = viewData,
        onViewAction = {
            with(viewModel) {
                onViewAction(it)
            }
        },
    )

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect { event ->
            when (event.consume()) {
                MoreViewEvent.OpenAppSettings -> context.openAppSettings()
                null -> Unit
            }
        }
    }
}

context(MoreViewModel)
private fun onViewAction(viewAction: MoreViewAction) {
    when (viewAction) {
        MoreViewAction.OnClickReviewPermissions -> onClickReviewPermissions()
    }
}
