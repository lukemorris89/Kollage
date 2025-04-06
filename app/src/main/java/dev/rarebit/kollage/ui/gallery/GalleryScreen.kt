package dev.rarebit.kollage.ui.gallery

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.rarebit.core.permission.hasCameraPermission
import dev.rarebit.kollage.navigation.AppRoute
import dev.rarebit.kollage.ui.gallery.data.GalleryViewEvent
import dev.rarebit.kollage.ui.gallery.data.GalleryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GalleryScreen(
    navHostController: NavHostController,
    viewModel: GalleryViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val viewData by viewModel.viewData.collectAsStateWithLifecycle()

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
            when (event.consume()) {
                GalleryViewEvent.NavigateToTutorial -> {
                    navHostController.navigate(AppRoute.Tutorial)
                }

                GalleryViewEvent.NavigateToNewCollage -> {
                    if (context.hasCameraPermission()) {
                        navHostController.navigate(AppRoute.CreateCollage)
                    } else {
                        navHostController.navigate(AppRoute.Permissions)
                    }
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
        is GalleryViewAction.OnClickThumbnail -> TODO()
        is GalleryViewAction.OnClickThumbnailSelectMode -> toggleSelectedCollageForDeletion(
            viewAction.collage
        )

        is GalleryViewAction.OnLongClickThumbnail -> toggleSelectMode()
        GalleryViewAction.OnClickSelect -> toggleSelectMode()
        GalleryViewAction.OnClickDelete -> updateShowDeleteDialog(true)
        GalleryViewAction.OnConfirmDelete -> deleteSelectedCollages()
        GalleryViewAction.OnDismissConfirmDeleteDialog -> updateShowDeleteDialog(false)
    }
}
