package ru.app.incredible.pets.ui.gallery

import me.dmdev.rxpm.action
import me.dmdev.rxpm.state
import ru.app.incredible.pets.OpenFullImageScreen
import ru.app.incredible.pets.domain.GalleryInteractor
import ru.app.incredible.pets.ui.common.BasePm
import ru.app.incredible.pets.ui.gallery.adapter.PetItem
import java.util.ArrayList

class GalleryPm(
    private val galleryInteractor: GalleryInteractor
) : BasePm() {

    val images = state<List<PetItem>> {
        galleryInteractor.execute()
            .map { imageList ->
                imageList.map { PetItem(it.toString()) }
            }
    }

    val openImageClicks = action<String> {
        this.doOnNext { imageId ->
            sendMessage(OpenFullImageScreen(imageId, images.value as ArrayList<PetItem>))
        }
    }
}