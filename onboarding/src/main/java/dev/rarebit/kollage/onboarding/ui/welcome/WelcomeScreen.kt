package dev.rarebit.kollage.onboarding.ui.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import dev.rarebit.kollage.navigation.AppRoute
import dev.rarebit.kollage.onboarding.ui.welcome.data.WelcomeViewEvent
import dev.rarebit.kollage.onboarding.ui.welcome.data.WelcomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    viewModel: WelcomeViewModel = koinViewModel(),
) {
    val viewData = viewModel.viewData.collectAsState()
    WelcomeContent(
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
                WelcomeViewEvent.NavigateToHomeScreen -> {
                    navHostController.navigate(AppRoute.Home)
                }
                else -> Unit
            }
        }
    }
}

context(WelcomeViewModel)
private fun onViewAction(viewAction: WelcomeViewAction) {
    when (viewAction) {
        WelcomeViewAction.OnClickPrimaryCta -> onPrimaryCtaClicked()
        else -> {}
    }
}
