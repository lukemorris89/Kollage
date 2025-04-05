package dev.rarebit.kollage.ui.createcollage.camera

import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
import androidx.camera.core.CameraSelector.DEFAULT_FRONT_CAMERA
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.resolutionselector.AspectRatioStrategy
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import dev.rarebit.core.logger.Logger
import dev.rarebit.design.component.tools.FloatingToolRow
import dev.rarebit.design.component.VerticalSpacer
import dev.rarebit.design.component.tools.CollageTool
import dev.rarebit.design.modifier.regularScreen
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.collage.CollageContent
import dev.rarebit.kollage.ui.createcollage.collage.createCollageLayer
import dev.rarebit.kollage.ui.createcollage.collage.component.CollageToolRow
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.AlphaRowContent
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.ColourRowContent
import dev.rarebit.kollage.ui.createcollage.collage.component.secondarytools.CropShapeRowContent
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import dev.rarebit.kollage.ui.createcollage.util.camerautil.getCameraProvider
import dev.rarebit.kollage.ui.createcollage.util.imageutil.flipHorizontally
import dev.rarebit.kollage.ui.createcollage.util.imageutil.rotate
import dev.rarebit.kollage.ui.createcollage.util.imageutil.toBitmap
import org.koin.compose.koinInject

@Composable
fun CameraContent(
    viewData: CreateCollageViewData,
    contentPadding: PaddingValues,
    onViewAction: (CreateCollageViewAction) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val executor = ContextCompat.getMainExecutor(context)
    val logger = koinInject<Logger>()

    // Set aspect ratio and resolution selector to ensure preview and image capture images are the same
    val aspectRatioStrategy = AspectRatioStrategy(
        AspectRatio.RATIO_16_9,
        AspectRatioStrategy.FALLBACK_RULE_AUTO
    )
    val resolutionSelector = ResolutionSelector.Builder()
        .setAspectRatioStrategy(aspectRatioStrategy)
        .build()

    val preview = remember(viewData.cameraLensFacing) {
        Preview.Builder()
            .setResolutionSelector(resolutionSelector)
            .build()
    }
    val previewView = remember { PreviewView(context) }

    val imageCapture: ImageCapture = remember {
        ImageCapture.Builder()
            .setResolutionSelector(resolutionSelector)
            .setCaptureMode(CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
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

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    )

    CollageContent(
        viewData = viewData,
        onViewAction = onViewAction,
        onCreateCollageLayer = { cropRect ->
            imageCapture.takePicture(
                executor,
                @ExperimentalGetImage object : ImageCapture.OnImageCapturedCallback() {
                    override fun onError(exception: ImageCaptureException) {
                        logger.logError(exception) { "Photo capture failed: ${exception.message}" }
                    }

                    override fun onCaptureSuccess(imageProxy: ImageProxy) {
                        onViewAction(CreateCollageViewAction.OnCreateCollageLayer(imageProxy, cropRect))
                    }
                }
            )
        },
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .regularScreen()
            .padding(contentPadding)
            .safeDrawingPadding(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FloatingToolRow(
            isVisible = viewData.showFloatingToolRow,
            content = {
                viewData.selectedSecondaryTool?.let {
                    when (it) {
                        CollageTool.SHAPE ->
                            CropShapeRowContent(
                                viewData = viewData,
                                onViewAction = onViewAction,
                            )

                        CollageTool.ALPHA ->
                            AlphaRowContent(
                                viewData = viewData,
                                onViewAction = onViewAction,
                            )

                        CollageTool.COLOUR ->
                            ColourRowContent(
                                viewData = viewData,
                                onViewAction = onViewAction,
                            )

                        else -> Unit
                    }
                }
            }
        )
        VerticalSpacer(paddingLarge)
        CollageToolRow(
            viewData = viewData,
            onViewAction = onViewAction,
            onCaptureCollage = {
                imageCapture.takePicture(
                    executor,
                    @ExperimentalGetImage object : ImageCapture.OnImageCapturedCallback() {
                        override fun onError(exception: ImageCaptureException) {
                            onError(exception)
                        }

                        override fun onCaptureSuccess(imageProxy: ImageProxy) {
                            imageProxy.image?.let {
                                val bitmap = it.toBitmap()
                                    .rotate(imageProxy.imageInfo.rotationDegrees.toFloat())
                                    .flipHorizontally(viewData.cameraLensFacing)
                                    .asImageBitmap()

                                onViewAction(CreateCollageViewAction.OnDoneClicked(bitmap))
                                imageProxy.close()
                            }
                        }
                    }
                )
            }
        )
    }
}
