package ru.app.incredible.pets.di

import org.koin.dsl.module
import ru.app.incredible.pets.ui.cats.CatsPm
import ru.app.incredible.pets.ui.dogs.DogsPm
import ru.app.incredible.pets.ui.main.MainBottomBarPm

object PmModule {

    fun create() = module {
        factory { MainBottomBarPm() }
        factory { DogsPm() }
        factory { CatsPm() }
    }
}