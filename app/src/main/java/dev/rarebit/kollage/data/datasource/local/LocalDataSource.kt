package dev.rarebit.kollage.data.datasource.local

import dev.rarebit.kollage.data.database.model.CollageData
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    var hasCompletedTutorial: Boolean

    fun updateHasCompletedTutorial(hasCompleted: Boolean)

    fun getAllCollages(): Flow<List<CollageData>>
    suspend fun getCollage(id: Int): CollageData
    suspend fun saveCollage(imagePath: String)
    suspend fun updateCollage(kollage: CollageData)
    suspend fun deleteCollage(kollage: CollageData)
    suspend fun deleteCollages(collages: List<CollageData>)
}
