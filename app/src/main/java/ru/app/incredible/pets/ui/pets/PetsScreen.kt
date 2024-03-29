package ru.app.incredible.pets.ui.pets

import android.graphics.drawable.Drawable
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.NoTransition
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.request.transition.TransitionFactory
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.view.visibility
import kotlinx.android.synthetic.main.screen_pets.*
import kotlinx.android.synthetic.main.toolbar.view.*
import me.dmdev.rxpm.bindTo
import me.dmdev.rxpm.passTo
import me.dmdev.rxpm.widget.bindTo
import ru.app.incredible.pets.R
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.domain.ImageDownloadState
import ru.app.incredible.pets.extensions.visible
import ru.app.incredible.pets.ui.common.BaseScreen
import kotlin.math.hypot

class PetsScreen : BaseScreen<PetsPm>() {

    override val screenLayout = R.layout.screen_pets

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

        pm.imageDownloadStatus bindTo { downloadState ->
            view?.toolbar?.menu?.findItem(R.id.downloadImage)?.isVisible =
                downloadState == ImageDownloadState.IDLE && downloadState != ImageDownloadState.PROGRESS

            view?.toolbar?.menu?.findItem(R.id.removeImage)?.isVisible =
                downloadState == ImageDownloadState.FINISHED

            progressDownloadImage.visible(downloadState == ImageDownloadState.PROGRESS)
        }

        pm.progress bindTo progressPet.visibility()
        pm.updateImageButtonEnabled bindTo updateImageButton::setEnabled

        updateImageButton.clicks() bindTo pm.updateImageButtonClicks

        pm.dogImageUrl bindTo {
            Glide.with(this)
                .load(it)
                .into(petImage)
        }

        pm.messageDialog bindTo { message, _ ->
            AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setPositiveButton(R.string.close, null)
                .create()
        }
    }
}