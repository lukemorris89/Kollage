package dev.rarebit.kollage.ui.collageresult

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import dev.rarebit.design.component.tools.CollageTool
import dev.rarebit.kollage.navigation.AppRoute
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewEvent
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CollageResultScreen(
    navHostController: NavHostController,
    viewModel: CollageResultViewModel = koinViewModel()
) {
    val viewData by viewModel.viewData.collectAsState()
    CollageResultContent(
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
                CollageResultViewEvent.NavigateBack -> navHostController.popBackStack()
                CollageResultViewEvent.NavigateToHomeScreen -> {
                    val navOptions = NavOptions.Builder().apply {
                        setPopUpTo<AppRoute.Home>(true)
                    }.build()
                    navHostController.navigate(AppRoute.Home, navOptions)
                }
                null -> Unit
            }
        }
    }
}

context(CollageResultViewModel)
@Suppress("CyclomaticComplexMethod")
private fun onViewAction(viewAction: CollageResultViewAction) {
    when (viewAction) {
        CollageResultViewAction.OnBackPressed -> onBackPressed()
        is CollageResultViewAction.OnToolClicked -> updateSelectedTool(viewAction.tool)
        is CollageResultViewAction.OnUpdateBackground -> updateBackgroundSelection(viewAction.backgroundSelection)
        is CollageResultViewAction.OnUpdateImageFormat -> updateImageFormat(viewAction.imageFormat)
        CollageResultViewAction.OnSaveClicked -> {
            updateSelectedTool(CollageTool.SAVE)
            saveFinalBitmap()
        }
    }
}