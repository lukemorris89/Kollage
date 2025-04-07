package dev.rarebit.kollage.ui.collageresult.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewData
import dev.rarebit.kollage.ui.createcollage.util.aspectratio.AspectRatioReference
import dev.rarebit.kollage.ui.createcollage.util.aspectratio.aspectRatioReference
import dev.rarebit.kollage.ui.createcollage.util.imageutil.BackgroundSelection

@Composable
fun CollageResultPreview(
    viewData: CollageResultViewData,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Set background
        when (viewData.backgroundSelection) {
            BackgroundSelection.CAMERA -> Image(
                modifier = Modifier
                    .aspectRatioReference(
                        ratioWidth = 16f,
                        ratioHeight = 9f,
                        AspectRatioReference.PARENT_HEIGHT
                    ),
                bitmap = viewData.backgroundBitmap,
                contentDescription = null,
            )

            BackgroundSelection.COLOUR -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(viewData.backgroundColor.colour),
            )
        }

        // Set foreground
        viewData.collage?.let {
            Image(
                modifier = Modifier
                    .aspectRatioReference(
                        ratioWidth = 16f,
                        ratioHeight = 9f,
                        AspectRatioReference.PARENT_HEIGHT
                    ),
                bitmap = it,
                contentDescription = null,
            )
        }
    }
}
