package ru.app.incredible.pets.di

import org.koin.dsl.module
import ru.app.incredible.pets.ui.cat.CatPm
import ru.app.incredible.pets.ui.dog.DogPm
import ru.app.incredible.pets.ui.main.MainBottomBarPm

object PmModule {

    fun create() = module {
        factory { MainBottomBarPm() }
        factory { DogPm(get(), get()) }
        factory { CatPm(get(), get()) }
    }
}