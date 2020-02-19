package ru.app.incredible.pets.ui.dog

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.visibility
import kotlinx.android.synthetic.main.dog_screen.*
import kotlinx.android.synthetic.main.toolbar.view.*
import me.dmdev.rxpm.bindTo
import ru.app.incredible.pets.R
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.ui.common.BaseScreen

class DogScreen : BaseScreen<DogPm>() {

    override val screenLayout = R.layout.dog_screen

    override fun providePresentationModel(): DogPm = getKoin().get()

    override fun onInitView() {
        super.onInitView()

        view?.toolbar?.setTitle(R.string.dog)
    }

    override fun onBindPresentationModel(pm: DogPm) {
        super.onBindPresentationModel(pm)

        pm.progressDog bindTo progressDog.visibility()
        pm.updateImageButtonEnabled bindTo updateImageButton::setEnabled

        updateImageButton.clicks() bindTo pm.updateImageButtonClicks

        pm.dogImageUrl bindTo {
            Glide.with(this)
                .load(it)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(dogImage)
        }
    }
}