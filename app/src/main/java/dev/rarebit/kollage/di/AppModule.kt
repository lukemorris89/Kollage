package dev.rarebit.kollage.di

import dev.rarebit.core.di.coreModule
import dev.rarebit.kollage.onboarding.di.onboardingModule
import dev.rarebit.kollage.ui.home.data.HomeViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    listOf(
        coreModule,
        onboardingModule,
    ).let(::loadKoinModules)

    viewModel { HomeViewModel(get()) }
}
