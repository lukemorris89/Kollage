package dev.rarebit.kollage

import android.app.Application
import dev.rarebit.kollage.di.appModule
import org.koin.android.ext.koin.androidContext
import timber.log.Timber

class MainApplication: Application() {
    private fun startKoin() {
        org.koin.core.context.startKoin {
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        startKoin()
    }
}