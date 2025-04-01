package dev.rarebit.kollage.ui.createcollage.camera

import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CameraContent(
    viewData: CreateCollageViewData,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // setTargetAspectRatio deprecated but required fro preview and capture to match size
    val preview = Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_16_9).build()
    val previewView = remember { PreviewView(context) }

    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder().setCaptureMode(CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetAspectRatio(AspectRatio.RATIO_16_9).build()
    }

    LaunchedEffect(uiState.lensFacing) {
        val cameraSelector = CameraSelector.Builder().requireLensFacing(uiState.lensFacing).build()

        val cameraProvider = context.getCameraProvider()
        with(cameraProvider) {
            viewModel.updateHasCameras(
                hasBackCamera = hasCamera(DEFAULT_BACK_CAMERA),
                hasFrontCamera = hasCamera(DEFAULT_FRONT_CAMERA),
            )
        }

        val viewport = previewView.viewPort

        viewport?.let {
            val useCaseGroup = UseCaseGroup.Builder()
                .setViewPort(it)
                .addUseCase(preview)
                .addUseCase(imageCapture)
                .build()

            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                useCaseGroup,
            )
            preview.setSurfaceProvider(previewView.surfaceProvider)
            viewModel.updateHasTorch(camera.cameraInfo.hasFlashUnit())

            viewModel.uiState.collectLatest { state ->
                if (camera.cameraInfo.hasFlashUnit()) {
                    camera.cameraControl.enableTorch(state.torchOn)
                }
            }
        }
    }
}