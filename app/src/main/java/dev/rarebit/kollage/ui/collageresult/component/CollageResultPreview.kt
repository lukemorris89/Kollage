package dev.rarebit.kollage.ui.collageresult.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewData
import dev.rarebit.kollage.ui.createcollage.util.aspectratio.AspectRatioReference
import dev.rarebit.kollage.ui.createcollage.util.aspectratio.aspectRatioReference
import dev.rarebit.kollage.ui.createcollage.util.imageutil.BackgroundSelection

@Composable
fun CollageResultPreview(
    viewData: CollageResultViewData,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        if (viewData.backgroundSelection == BackgroundSelection.CAMERA) {
            viewData.finalBitmap?.let {
                Image(
                    modifier = Modifier
                        .aspectRatioReference(
                            ratioWidth = 16f,
                            ratioHeight = 9f,
                            AspectRatioReference.PARENT_HEIGHT
                        )
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                    bitmap = it,
                    contentDescription = null,
                )
            }
        } else {
            viewData.collage?.image?.let {
                Box(modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .background(viewData.backgroundColor),
                ) {
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
    }
}
