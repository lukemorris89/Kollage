package dev.rarebit.kollage.ui.createcollage.collage

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import dev.rarebit.kollage.ui.createcollage.CreateCollageViewAction
import dev.rarebit.kollage.ui.createcollage.collage.component.CropShapeView
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import dev.rarebit.kollage.ui.createcollage.util.aspectratio.AspectRatioReference
import dev.rarebit.kollage.ui.createcollage.util.aspectratio.aspectRatioReference
import kotlin.math.min

@Composable
fun CollageContent(
    viewData: CreateCollageViewData,
    onViewAction: (CreateCollageViewAction) -> Unit,
    onCreateCollageLayer: (Rect) -> Unit,
) {
    val animatedAlpha by animateFloatAsState(
        targetValue = viewData.selectedAlpha,
        animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing),
        label = "Collage layer alpha animation",
    )
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        (viewData.previousCollageLayer ?: viewData.currentCollageLayer)?.let {
            Image(
                modifier = Modifier
                    .aspectRatioReference(
                        ratioWidth = 16f,
                        ratioHeight = 9f,
                        AspectRatioReference.MAX_PARENT_WIDTH_PARENT_HEIGHT
                    )
                    .graphicsLayer {
                        alpha = animatedAlpha
                    }
                    .align(Alignment.Center),
                bitmap = (
                    viewData.currentCollageLayer
                        ?: viewData.previousCollageLayer
                    )!!.image,
                contentDescription = null,
            )
        }
        CropShapeView(
            viewData = viewData,
            onStart = {
                onViewAction(
                    CreateCollageViewAction.OnAlphaChanged(
                        min(
                            0.5f,
                            viewData.defaultAlpha
                        )
                    )
                )
            },
            onRelease = { rect ->
                onCreateCollageLayer(rect)
                onViewAction(
                    CreateCollageViewAction.OnAlphaChanged(viewData.defaultAlpha)
                )
            }
        )
    }
}
