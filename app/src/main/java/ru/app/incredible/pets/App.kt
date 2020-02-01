package ru.app.incredible.pets

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import ru.app.incredible.pets.di.*
import timber.log.Timber

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
        initLogger()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(allModules())
        }
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun allModules(): List<Module> {
        return listOf(
            AppModule.create(),
            NetworkModule.create(),
            GatewayModule.create(),
            InteractorModule.create(),
            PmModule.create()
        )
    }
}