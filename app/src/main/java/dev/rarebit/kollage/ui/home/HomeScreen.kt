package dev.rarebit.kollage.ui.home

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.rarebit.kollage.ui.home.data.HomeViewEvent
import dev.rarebit.kollage.ui.home.data.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    BackHandler {
        viewModel.onBackPressed()
    }

    val viewData by viewModel.viewData.collectAsStateWithLifecycle()
    HomeContent(
        viewData = viewData,
        onViewAction = {
            with(viewModel) {
                onViewAction(it)
            }
        },
        navHostController = navHostController,
    )

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect { event ->
            when (event) {
                HomeViewEvent.NavigateToNewCollage -> {
                    // TODO add navigation to tutorial or new collage
                }

                HomeViewEvent.NavigateBack -> {
                    navHostController.popBackStack()
                }

                null -> Unit
            }
        }
    }
}

context(HomeViewModel)
private fun onViewAction(viewAction: HomeViewAction) {
    when (viewAction) {
        is HomeViewAction.OnClickBottomNavigationTab -> onClickBottomNavigationTab(viewAction.item)
    }
}
