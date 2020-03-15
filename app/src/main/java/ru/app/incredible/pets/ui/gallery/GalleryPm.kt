package ru.app.incredible.pets.ui.gallery

import me.dmdev.rxpm.state
import ru.app.incredible.pets.domain.GalleryInteractor
import ru.app.incredible.pets.ui.common.BasePm
import ru.app.incredible.pets.ui.gallery.adapter.PetItem

class GalleryPm(
    private val galleryInteractor: GalleryInteractor
) : BasePm() {

    val images = state<List<PetItem>>()

    override fun onCreate() {
        super.onCreate()

        galleryInteractor.execute()
            .map { imageList ->
                imageList.map { PetItem(it.toString()) }
            }
            .subscribe(images.consumer)
            .untilDestroy()
    }
}