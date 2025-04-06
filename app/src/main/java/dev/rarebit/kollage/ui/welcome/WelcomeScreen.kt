package dev.rarebit.kollage.ui.welcome

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import dev.rarebit.kollage.navigation.AppRoute
import dev.rarebit.kollage.ui.welcome.data.WelcomeViewEvent
import dev.rarebit.kollage.ui.welcome.data.WelcomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WelcomeScreen(
    navHostController: NavHostController,
    viewModel: WelcomeViewModel = koinViewModel(),
) {
    val viewData = viewModel.viewData.collectAsStateWithLifecycle()
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
                    val navOptions = NavOptions.Builder().apply {
                        setPopUpTo<AppRoute.Home>(true)
                    }.build()
                    navHostController.navigate(AppRoute.Home, navOptions)
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
