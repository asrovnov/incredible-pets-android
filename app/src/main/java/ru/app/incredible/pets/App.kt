package ru.app.incredible.pets

import android.app.Application
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
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
        initFileDownloader()
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

    private fun initFileDownloader() {
        FileDownloader.setupOnApplicationOnCreate(this)
            .connectionCreator(
                FileDownloadUrlConnection.Creator(
                    FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000)
                        .readTimeout(15000)
                )
            )
            .commit()
    }
}