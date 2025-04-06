package dev.rarebit.kollage.ui.createcollage.collage

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ImageBitmap

data class CollageLayer(
    val image: ImageBitmap,
    val rect: Rect,
)
