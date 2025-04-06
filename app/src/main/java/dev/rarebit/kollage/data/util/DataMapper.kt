package dev.rarebit.kollage.data.util

import androidx.core.net.toUri
import dev.rarebit.kollage.data.database.model.CollageData
import dev.rarebit.kollage.data.model.Collage

fun CollageData.toCollage(): Collage {
    return Collage(
        id = id,
        uri = imagePath.toUri(),
        dateCreated = dateCreated
    )
}

fun Collage.toCollageData(): CollageData {
    return CollageData(
        id = id,
        imagePath = uri.toString(),
        dateCreated = dateCreated
    )
}
