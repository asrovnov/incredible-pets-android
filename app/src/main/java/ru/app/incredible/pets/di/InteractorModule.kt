package ru.app.incredible.pets.di

import org.koin.dsl.module
import ru.app.incredible.pets.domain.RandomCatInteractor
import ru.app.incredible.pets.domain.RandomDogInteractor

object InteractorModule {

    fun create() = module {
        factory { RandomDogInteractor(get(), get()) }
        factory { RandomCatInteractor(get(), get()) }
    }
}