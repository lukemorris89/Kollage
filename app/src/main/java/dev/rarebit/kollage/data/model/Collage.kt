package dev.rarebit.kollage.data.model

import android.net.Uri
import dev.rarebit.kollage.util.serialization.OffsetDateTimeSerializer
import dev.rarebit.kollage.util.serialization.UriSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class Collage(
    val id: Int,
    @Serializable(with = UriSerializer::class) val uri: Uri,
    @Serializable(with = OffsetDateTimeSerializer::class) val dateCreated: OffsetDateTime
)
