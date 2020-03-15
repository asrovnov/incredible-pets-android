package ru.app.incredible.pets.di

import org.koin.dsl.module
import ru.app.incredible.pets.domain.*

object InteractorModule {

    fun create() = module {
        factory { RandomDogInteractor(get(), get()) }
        factory { RandomCatInteractor(get(), get()) }
        factory { DownloadImageInteractor(get()) }
        factory { GetDownloadStateInteractor(get()) }
        factory { RemoveImageInteractor(get()) }
        factory { GalleryInteractor(get(), get()) }
    }
}