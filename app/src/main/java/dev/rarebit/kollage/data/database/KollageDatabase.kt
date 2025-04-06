package dev.rarebit.kollage.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.rarebit.kollage.data.database.converter.Converters
import dev.rarebit.kollage.data.database.dao.CollageDao
import dev.rarebit.kollage.data.database.model.CollageData

@Database(entities = [CollageData::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class KollageDatabase : RoomDatabase() {
    abstract fun collageDao(): CollageDao
}
