package ru.app.incredible.pets

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import ru.app.incredible.pets.di.NetworkModule
import ru.app.incredible.pets.di.PmModule

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(allModules())
        }
    }

    private fun allModules(): List<Module> {
        return listOf(
            PmModule.create(),
            NetworkModule.create()
        )
    }
}