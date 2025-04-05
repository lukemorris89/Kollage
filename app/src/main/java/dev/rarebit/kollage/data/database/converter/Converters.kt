package dev.rarebit.kollage.data.database.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): OffsetDateTime? {
        return value?.let { Instant.ofEpochMilli(it).atOffset(ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateToTimestamp(date: OffsetDateTime?): Long? {
        return date?.let { it.toEpochSecond() * 1000L }
    }
}