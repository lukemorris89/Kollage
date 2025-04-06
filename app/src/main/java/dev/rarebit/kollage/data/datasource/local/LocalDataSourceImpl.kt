package dev.rarebit.kollage.data.datasource.local

import android.content.SharedPreferences
import androidx.core.content.edit
import dev.rarebit.kollage.data.database.dao.CollageDao
import dev.rarebit.kollage.data.database.model.CollageData
import java.time.OffsetDateTime

class LocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val collageDao: CollageDao
) : LocalDataSource {
    override var hasCompletedTutorial: Boolean
        get() = sharedPreferences.getBoolean(KEY_HAS_COMPLETED_TUTORIAL, false)
        set(value) {
            sharedPreferences.edit {
                putBoolean(KEY_HAS_COMPLETED_TUTORIAL, value)
            }
        }

    override fun updateHasCompletedTutorial(hasCompleted: Boolean) {
        hasCompletedTutorial = hasCompleted
    }

    override fun getAllCollages() = collageDao.getAllCollages()

    override suspend fun getCollage(id: Int) = collageDao.getCollage(id)

    override suspend fun saveCollage(imagePath: String) =
        collageDao.insertCollage(
            CollageData(
                imagePath = imagePath,
                dateCreated = OffsetDateTime.now()
            )
        )

    override suspend fun updateCollage(collage: CollageData) = collageDao.updateCollage(collage)

    override suspend fun deleteCollage(collage: CollageData) = collageDao.deleteCollage(collage)

    companion object {
        private const val KEY_HAS_COMPLETED_TUTORIAL = "has_completed_tutorial"
    }
}
