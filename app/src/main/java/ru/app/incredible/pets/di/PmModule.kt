package ru.app.incredible.pets.di

import org.koin.dsl.module
import ru.app.incredible.pets.ui.gallery.GalleryPm
import ru.app.incredible.pets.ui.pets.PetsPm
import ru.app.incredible.pets.ui.main.MainBottomBarPm

object PmModule {

    fun create() = module {
        factory { MainBottomBarPm() }
        factory { PetsPm(get(), get(), get(), get(), get(), get()) }
        factory { GalleryPm(get()) }
    }
}