package dev.rarebit.kollage.data.repository

import dev.rarebit.kollage.data.datasource.local.LocalDataSource

class DataRepositoryImpl(private val localDataSource: LocalDataSource) : DataRepository {

    override fun getHasCompletedTutorial() = localDataSource.hasCompletedTutorial

    override fun updateHasCompletedTutorial(hasCompleted: Boolean) {
        localDataSource.updateHasCompletedTutorial(hasCompleted)
    }
}
