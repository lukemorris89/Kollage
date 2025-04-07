package dev.rarebit.kollage.ui.createcollage.collage

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import dev.rarebit.kollage.ui.createcollage.collage.component.CropShapeView
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewData
import dev.rarebit.kollage.ui.createcollage.util.aspectratio.AspectRatioReference
import dev.rarebit.kollage.ui.createcollage.util.aspectratio.aspectRatioReference

@Composable
fun CollageContent(
    viewData: CreateCollageViewData,
    onCreateCollageLayer: (Rect) -> Unit,
) {
    var isUserDragActive by remember { mutableStateOf(false) }
    val animatedPreviousCollageAlpha by animateFloatAsState(
        targetValue = if (isUserDragActive) {
            0.5f
        } else {
            1f
        },
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
                        alpha = animatedPreviousCollageAlpha
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
                isUserDragActive = true
            },
            onRelease = { rect ->
                onCreateCollageLayer(rect)
                isUserDragActive = false
            }
        )
    }
}
