package dev.rarebit.kollage.ui.createcollage.util.imageutil

import android.graphics.Bitmap

enum class ImageFormat(val value: String, val mimeType: String, val compressionFormat: Bitmap.CompressFormat) {
    PNG(".png", "image/png", Bitmap.CompressFormat.PNG),
    JPEG(".jpeg", "image/jpeg", Bitmap.CompressFormat.JPEG),
    WEBP(".webp", "image/webp", Bitmap.CompressFormat.WEBP_LOSSLESS)
}
