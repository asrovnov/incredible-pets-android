package ru.app.incredible.pets.ui.gallery

import kotlinx.android.synthetic.main.toolbar.view.*
import ru.app.incredible.pets.R
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.ui.common.BaseScreen

class GalleryScreen : BaseScreen<GalleryPm>() {

    override val screenLayout = R.layout.gallery_screen

    override fun providePresentationModel(): GalleryPm = getKoin().get()

    override fun onInitView() {
        super.onInitView()

        view?.toolbar?.setTitle(R.string.gallery)
    }

    override fun onBindPresentationModel(pm: GalleryPm) {
        super.onBindPresentationModel(pm)

    }
}