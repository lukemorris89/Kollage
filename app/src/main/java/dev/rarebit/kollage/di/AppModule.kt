package dev.rarebit.kollage.di

import dev.rarebit.core.di.coreModule
import dev.rarebit.kollage.onboarding.di.onboardingModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val appModule = module {
    listOf(
        coreModule,
        onboardingModule,
    ).let(::loadKoinModules)
}
