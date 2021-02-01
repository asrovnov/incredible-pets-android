package ru.app.incredible.pets.di

import org.koin.dsl.module
import ru.app.incredible.pets.domain.*
import ru.app.incredible.pets.domain.cat.RandomCatInteractor
import ru.app.incredible.pets.domain.dog.RandomDogInteractor
import ru.app.incredible.pets.domain.DownloadImageInteractor
import ru.app.incredible.pets.domain.GetDownloadStateInteractor

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