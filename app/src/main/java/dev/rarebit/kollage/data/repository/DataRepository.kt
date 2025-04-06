package dev.rarebit.kollage.data.repository

interface DataRepository {
    fun getHasCompletedTutorial(): Boolean
    fun updateHasCompletedTutorial(hasCompleted: Boolean)
}
