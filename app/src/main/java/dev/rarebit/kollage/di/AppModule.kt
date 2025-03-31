package dev.rarebit.kollage.di

import dev.rarebit.core.di.coreModule
import dev.rarebit.kollage.onboarding.di.onboardingModule
import dev.rarebit.kollage.ui.gallery.data.GalleryViewModel
import dev.rarebit.kollage.ui.home.data.HomeViewModel
import dev.rarebit.kollage.ui.more.data.MoreViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    listOf(
        coreModule,
        onboardingModule,
    ).let(::loadKoinModules)

    viewModel { HomeViewModel(get()) }
    viewModel { GalleryViewModel(get()) }
    viewModel { MoreViewModel(get()) }
}
