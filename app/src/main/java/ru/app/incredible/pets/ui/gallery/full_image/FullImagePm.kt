package ru.app.incredible.pets.ui.gallery.full_image

import me.dmdev.rxpm.state
import ru.app.incredible.pets.ui.common.BasePm
import ru.app.incredible.pets.ui.gallery.adapter.PetItem
import java.util.ArrayList

class FullImagePm(
    imageList: ArrayList<PetItem>
) : BasePm() {

    val images = state(imageList.toList())
}