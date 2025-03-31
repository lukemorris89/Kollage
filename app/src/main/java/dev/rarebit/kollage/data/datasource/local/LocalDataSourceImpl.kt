package dev.rarebit.kollage.data.datasource.local

import android.content.SharedPreferences
import androidx.core.content.edit

class LocalDataSourceImpl(
    private val sharedPreferences: SharedPreferences
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

    companion object {
        private const val KEY_HAS_COMPLETED_TUTORIAL = "has_completed_tutorial"
    }
}
