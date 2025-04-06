package dev.rarebit.kollage.data.model

import android.net.Uri
import java.time.OffsetDateTime

data class Collage(
    val id: Int,
    val uri: Uri,
    val dateCreated: OffsetDateTime
)
