package dev.rarebit.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dev.rarebit.core.application.ApplicationContextProvider
import dev.rarebit.core.application.ApplicationProvider
import dev.rarebit.core.buildinfo.BuildInfoProvider
import dev.rarebit.core.buildinfo.BuildInfoProviderImpl
import dev.rarebit.core.logger.Logger
import dev.rarebit.core.logger.LoggerImpl
import dev.rarebit.core.view.ResourceProvider
import dev.rarebit.core.view.ResourceProviderImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single<BuildInfoProvider> { BuildInfoProviderImpl() }
    single<ResourceProvider> { ResourceProviderImpl(get()) }
    factory<ApplicationContextProvider> {
        object : ApplicationContextProvider {
            override fun invoke(): Context = androidContext()
        }
    }
    factory<ApplicationProvider> {
        object : ApplicationProvider {
            override fun invoke(): Application = androidApplication()
        }
    }
    single<Logger> { LoggerImpl() }
    single<SharedPreferences> {
        androidContext().getSharedPreferences(
            "kollage_shared_preferences",
            Context.MODE_PRIVATE
        )
    }
}
