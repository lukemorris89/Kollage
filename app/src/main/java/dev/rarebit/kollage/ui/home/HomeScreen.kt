package dev.rarebit.kollage.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import dev.rarebit.kollage.ui.home.data.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val viewData by viewModel.viewData.collectAsState()
    HomeContent(
        viewData = viewData,
        onViewAction = {
            with(viewModel) {
                onViewAction(it)
            }
        },
    )

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect { event ->
            when (event) {
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
