package dev.rarebit.kollage.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import dev.rarebit.kollage.data.database.model.CollageData
import kotlinx.coroutines.flow.Flow

@Dao
interface CollageDao {

    @Query("SELECT * FROM collage ORDER BY date_created")
    fun getAllCollages(): Flow<List<CollageData>>

    @Query("SELECT * FROM collage WHERE id = :id")
    suspend fun getCollage(id: Int): CollageData

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCollage(kollage: CollageData)

    @Update
    suspend fun updateCollage(kollage: CollageData)

    @Delete
    suspend fun deleteCollage(kollage: CollageData)

    @Delete
    suspend fun deleteCollages(collages: List<CollageData>)
}
