package dev.rarebit.kollage.ui.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import dev.rarebit.kollage.navigation.AppRoute
import dev.rarebit.kollage.ui.gallery.data.GalleryViewEvent
import dev.rarebit.kollage.ui.gallery.data.GalleryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GalleryScreen(
    navHostController: NavHostController,
    viewModel: GalleryViewModel = koinViewModel()
) {
    val viewData by viewModel.viewData.collectAsState()
    GalleryContent(
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
                GalleryViewEvent.NavigateToTutorial -> {
                    navHostController.navigate(AppRoute.Tutorial)
                }
                GalleryViewEvent.NavigateToNewCollage -> {
                    // TODO add navigation to tutorial or new collage
                }

                null -> Unit
            }
        }
    }
}

context(GalleryViewModel)
private fun onViewAction(viewAction: GalleryViewAction) {
    when (viewAction) {
        is GalleryViewAction.OnClickCreateNew -> onClickAddNewCollage()
    }
}
