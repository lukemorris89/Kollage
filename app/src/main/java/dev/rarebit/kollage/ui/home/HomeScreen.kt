package dev.rarebit.kollage.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import dev.rarebit.kollage.ui.home.data.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: HomeViewModel = koinViewModel()
) {
}