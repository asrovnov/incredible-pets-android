package ru.app.incredible.pets.ui.gallery

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.screen_gallery.*
import kotlinx.android.synthetic.main.toolbar.view.*
import me.dmdev.rxpm.bindTo
import ru.app.incredible.pets.R
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.ui.common.BaseScreen
import ru.app.incredible.pets.ui.gallery.adapter.GalleryAdapter

class GalleryScreen : BaseScreen<GalleryPm>() {

    override val screenLayout = R.layout.screen_gallery

    private val galleryAdapter = GalleryAdapter {
        // TODO: Add click item
    }

    override fun providePresentationModel(): GalleryPm = getKoin().get()

    override fun onInitView() {
        super.onInitView()

        view?.toolbar?.setTitle(R.string.gallery)

        with(recyclerView) {
            layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false)
            adapter = galleryAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        presentationModel.images bindTo {
            galleryAdapter.accept(it)
        }
    }
}