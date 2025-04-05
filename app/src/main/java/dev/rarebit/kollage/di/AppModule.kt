package dev.rarebit.kollage.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dev.rarebit.core.di.coreModule
import dev.rarebit.kollage.data.database.KollageDatabase
import dev.rarebit.kollage.data.database.dao.CollageDao
import dev.rarebit.kollage.data.datasource.local.LocalDataSource
import dev.rarebit.kollage.data.datasource.local.LocalDataSourceImpl
import dev.rarebit.kollage.data.repository.DataRepository
import dev.rarebit.kollage.data.repository.DataRepositoryImpl
import dev.rarebit.kollage.data.repository.collage.CollageRepository
import dev.rarebit.kollage.data.repository.collage.CollageRepositoryImpl
import dev.rarebit.kollage.onboarding.di.onboardingModule
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewModel
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewModel
import dev.rarebit.kollage.ui.gallery.data.GalleryViewModel
import dev.rarebit.kollage.ui.home.data.HomeViewModel
import dev.rarebit.kollage.ui.more.data.MoreViewModel
import dev.rarebit.kollage.ui.tutorial.data.TutorialViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    listOf(
        coreModule,
        onboardingModule,
    ).let(::loadKoinModules)

    single {
        Room.databaseBuilder((get()), KollageDatabase::class.java, "kollage_database")
            .build()
    }

    single<CollageDao> {
        val database = get<KollageDatabase>()
        database.collageDao()
    }
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<DataRepository> { DataRepositoryImpl(get()) }
    single<CollageRepository> { CollageRepositoryImpl(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { GalleryViewModel(get(), get()) }
    viewModel { MoreViewModel(get()) }
    viewModel { TutorialViewModel(get(), get()) }
    viewModel { CreateCollageViewModel(get(), get(), get()) }
    viewModel { CollageResultViewModel(get(), get(), get()) }
}
