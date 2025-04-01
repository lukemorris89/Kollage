package dev.rarebit.kollage.ui.createcollage.camera

import android.content.Context
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

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

    LaunchedEffect(viewData.cameraLensFacing) {
        val cameraSelector =
            CameraSelector.Builder().requireLensFacing(viewData.cameraLensFacing).build()

        val cameraProvider = context.getCameraProvider()
        with(cameraProvider) {
            onViewAction(
                CreateCollageViewAction.OnCamerasLoaded(
                    hasBackCamera = hasCamera(DEFAULT_BACK_CAMERA),
                    hasFrontCamera = hasCamera(DEFAULT_FRONT_CAMERA),
                )
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
            preview.surfaceProvider = previewView.surfaceProvider
            onViewAction(CreateCollageViewAction.OnTorchDetected(camera.cameraInfo.hasFlashUnit()))

            if (camera.cameraInfo.hasFlashUnit()) {
                camera.cameraControl.enableTorch(viewData.isTorchOn)
            }
        }
    }

    previewView?.let {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )
    }
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener(
                {
                    continuation
                        .resume(
                            cameraProvider.get()
                        )
                },
                ContextCompat.getMainExecutor(this)
            )
        }
    }
