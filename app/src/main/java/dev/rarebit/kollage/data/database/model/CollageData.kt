package dev.rarebit.kollage.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity(tableName = "collage")
data class CollageData(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "image_path")
    val imagePath: String,
    @ColumnInfo(name = "date_created")
    val dateCreated: OffsetDateTime,
)
