package ru.app.incredible.pets.di

import org.koin.dsl.module
import ru.app.incredible.pets.ui.gallery.full_image.FullImagePm
import ru.app.incredible.pets.ui.gallery.GalleryPm
import ru.app.incredible.pets.ui.pets.PetsPm
import ru.app.incredible.pets.ui.main.MainBottomBarPm

object PmModule {

    const val IMAGE_ID = "image_id"
    const val IMAGE_LIST = "image_list"

    fun create() = module {
        factory { MainBottomBarPm() }
        factory { PetsPm(get(), get(), get(), get(), get(), get()) }
        factory { GalleryPm(get()) }
        factory { FullImagePm(getProperty(IMAGE_LIST)) }
    }
}