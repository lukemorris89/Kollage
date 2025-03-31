package dev.rarebit.kollage.data.datasource.local

interface LocalDataSource {
    var hasCompletedTutorial: Boolean

    fun updateHasCompletedTutorial(hasCompleted: Boolean)
}
