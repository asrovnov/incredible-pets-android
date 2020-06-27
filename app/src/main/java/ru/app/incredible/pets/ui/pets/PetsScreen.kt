package ru.app.incredible.pets.ui.pets

import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.visibility
import kotlinx.android.synthetic.main.pets_screen.*
import kotlinx.android.synthetic.main.toolbar.view.*
import me.dmdev.rxpm.bindTo
import me.dmdev.rxpm.passTo
import me.dmdev.rxpm.widget.bindTo
import ru.app.incredible.pets.R
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.domain.ImageDownloadState
import ru.app.incredible.pets.ui.common.BaseScreen

class PetsScreen : BaseScreen<PetsPm>() {

    override val screenLayout = R.layout.pets_screen

    override fun providePresentationModel(): PetsPm = getKoin().get()

    override fun onInitView() {
        super.onInitView()

        view?.toolbar?.setTitle(R.string.pets)
        view?.toolbar?.inflateMenu(R.menu.download_image_action)
    }

    override fun onBindPresentationModel(pm: PetsPm) {
        super.onBindPresentationModel(pm)

        view?.toolbar?.inflateMenu(R.menu.remove_image_action)
        view?.toolbar?.menu?.findItem(R.id.removeImage)?.isVisible = false

        view?.toolbar?.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.downloadImage -> Unit passTo pm.downloadButtonClicks
                R.id.removeImage -> Unit passTo pm.removeButtonClicks
            }

            true
        }

        pm.imageDownloadStatus bindTo {
            view?.toolbar?.menu?.findItem(R.id.downloadImage)?.isVisible =
                it == ImageDownloadState.IDLE && it != ImageDownloadState.PROGRESS

            view?.toolbar?.menu?.findItem(R.id.removeImage)?.isVisible =
                it == ImageDownloadState.FINISHED

            progressDownloadImage.isVisible = it == ImageDownloadState.PROGRESS
        }

        pm.progress bindTo progressPet.visibility()
        pm.updateImageButtonEnabled bindTo updateImageButton::setEnabled

        updateImageButton.clicks() bindTo pm.updateImageButtonClicks

        pm.dogImageUrl bindTo {
            Glide.with(this)
                .load(it)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(petImage)
        }

        pm.messageDialog bindTo { message, _ ->
            AlertDialog.Builder(view!!.context)
                .setMessage(message)
                .setPositiveButton(R.string.close, null)
                .create()
        }
    }
}