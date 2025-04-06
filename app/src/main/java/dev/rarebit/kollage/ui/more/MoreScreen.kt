package dev.rarebit.kollage.ui.more

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.rarebit.core.permission.openAppSettings
import dev.rarebit.kollage.ui.more.data.MoreViewEvent
import dev.rarebit.kollage.ui.more.data.MoreViewModel
import dev.rarebit.kollage.util.webview.navigateWebView
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoreScreen(
    navHostController: NavHostController,
    viewModel: MoreViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val viewData by viewModel.viewData.collectAsStateWithLifecycle()
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
            when (val consumedEvent = event.consume()) {
                MoreViewEvent.OpenAppSettings -> context.openAppSettings()
                is MoreViewEvent.OpenWebView -> {
                    navigateWebView(navHostController, consumedEvent.url)
                }

                null -> Unit
            }
        }
    }
}

context(MoreViewModel)
private fun onViewAction(viewAction: MoreViewAction) {
    when (viewAction) {
        MoreViewAction.OnClickReviewPermissions -> onClickReviewPermissions()
        MoreViewAction.OnClickPrivacyPolicy -> onClickPrivacyPolicy()
    }
}
