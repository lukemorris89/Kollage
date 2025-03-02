package dev.rarebit.kollage.di

import dev.rarebit.core.di.coreModule
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

val appModule = module {
    listOf(
        coreModule,
    ).let(::loadKoinModules)
}
