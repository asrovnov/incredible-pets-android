package ru.app.incredible.pets.ui.cat

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.visibility
import kotlinx.android.synthetic.main.cat_screen.*
import kotlinx.android.synthetic.main.cat_screen.updateImageButton
import kotlinx.android.synthetic.main.toolbar.view.*
import me.dmdev.rxpm.bindTo
import ru.app.incredible.pets.R
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.ui.common.BaseScreen

class CatScreen : BaseScreen<CatPm>() {

    override val screenLayout = R.layout.cat_screen

    override fun providePresentationModel(): CatPm = getKoin().get()

    override fun onInitView() {
        super.onInitView()

        view?.toolbar?.setTitle(R.string.cat)
        view?.toolbar?.inflateMenu(R.menu.download_image_action)
    }

    override fun onBindPresentationModel(pm: CatPm) {
        super.onBindPresentationModel(pm)

        pm.progressCat bindTo progressCat.visibility()
        pm.updateImageButtonEnabled bindTo updateImageButton::setEnabled

        updateImageButton.clicks() bindTo pm.updateImageButtonClicks

        pm.catImageUrl bindTo {
            Glide.with(this)
                .load(it)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(catImage)
        }
    }
}