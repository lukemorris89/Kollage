package dev.rarebit.kollage.ui.createcollage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewEvent
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateCollageScreen(
    navHostController: NavHostController,
    viewModel: CreateCollageViewModel = koinViewModel()
) {
    val viewData by viewModel.viewData.collectAsState()
    CreateCollageContent(
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
                CreateCollageViewEvent.NavigateBack -> navHostController.popBackStack()
                null -> Unit
            }
        }
    }
}

context(CreateCollageViewModel)
@Suppress("CyclomaticComplexMethod")
private fun onViewAction(viewAction: CreateCollageViewAction) {
    when (viewAction) {
        CreateCollageViewAction.OnUndoCollageLayer -> undoCollageLayer()
        CreateCollageViewAction.OnSwitchCamera -> updateCameraLensFacing()
        CreateCollageViewAction.OnEditClicked -> toggleEdit()
        CreateCollageViewAction.OnDoneClicked -> onDoneClicked()

        CreateCollageViewAction.OnCropShapeClicked -> toggleCropShape()
        CreateCollageViewAction.OnAlphaClicked -> toggleAlpha()
        CreateCollageViewAction.OnColourClicked -> toggleColour()

        is CreateCollageViewAction.OnCropShapeChanged -> onCropShapeChanged(viewAction.shape)
        is CreateCollageViewAction.OnAlphaChanged -> onAlphaChanged(viewAction.alpha)
        is CreateCollageViewAction.OnColourChanged -> onColourChanged(viewAction.colour)
        CreateCollageViewAction.OnBackPressed -> onBackPressed()
        is CreateCollageViewAction.OnCamerasLoaded -> updateHasCameras(
            viewAction.hasBackCamera,
            viewAction.hasFrontCamera
        )

        CreateCollageViewAction.OnTorchClicked -> updateTorchOn()
        is CreateCollageViewAction.OnTorchDetected -> updateHasTorch(viewAction.hasTorch)
        is CreateCollageViewAction.OnCreateCollageLayer -> updateCollageLayer(viewAction.collageLayer)
    }
}
