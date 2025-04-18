package dev.rarebit.kollage.di

import androidx.room.Room
import dev.rarebit.core.di.coreModule
import dev.rarebit.kollage.data.database.KollageDatabase
import dev.rarebit.kollage.data.database.dao.CollageDao
import dev.rarebit.kollage.data.datasource.local.LocalDataSource
import dev.rarebit.kollage.data.datasource.local.LocalDataSourceImpl
import dev.rarebit.kollage.data.model.Collage
import dev.rarebit.kollage.data.repository.DataRepository
import dev.rarebit.kollage.data.repository.DataRepositoryImpl
import dev.rarebit.kollage.data.repository.collage.CollageRepository
import dev.rarebit.kollage.data.repository.collage.CollageRepositoryImpl
import dev.rarebit.kollage.ui.collageresult.data.CollageResultViewModel
import dev.rarebit.kollage.ui.createcollage.data.CreateCollageViewModel
import dev.rarebit.kollage.ui.gallery.data.GalleryViewModel
import dev.rarebit.kollage.ui.home.data.HomeViewModel
import dev.rarebit.kollage.ui.more.data.MoreViewModel
import dev.rarebit.kollage.ui.permissions.data.PermissionsViewModel
import dev.rarebit.kollage.ui.tutorial.data.TutorialViewModel
import dev.rarebit.kollage.ui.viewcollage.data.ViewCollageViewModel
import dev.rarebit.kollage.ui.welcome.data.WelcomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    listOf(
        coreModule,
    ).let(::loadKoinModules)

    single {
        Room.databaseBuilder((get()), KollageDatabase::class.java, "kollage_database")
            .build()
    }

    single<CollageDao> {
        val database = get<KollageDatabase>()
        database.collageDao()
    }
    single<LocalDataSource> { LocalDataSourceImpl(get(), get()) }
    single<DataRepository> { DataRepositoryImpl(get()) }
    single<CollageRepository> { CollageRepositoryImpl(get(), get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { GalleryViewModel(get(), get(), get()) }
    viewModel { MoreViewModel(get()) }
    viewModel { (isFromSettings: Boolean) ->
        TutorialViewModel(isFromSettings = isFromSettings, get(), get())
    }
    viewModel { CreateCollageViewModel(get(), get(), get()) }
    viewModel { CollageResultViewModel(get(), get(), get()) }
    viewModel { WelcomeViewModel(get()) }
    viewModel { PermissionsViewModel(get()) }
    viewModel { (collage: Collage) ->
        ViewCollageViewModel(collage = collage, get(), get())
    }
}
