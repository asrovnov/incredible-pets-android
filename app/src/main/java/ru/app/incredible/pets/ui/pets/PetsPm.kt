package ru.app.incredible.pets.ui.pets

import io.reactivex.Single
import me.dmdev.rxpm.action
import me.dmdev.rxpm.bindProgress
import me.dmdev.rxpm.state
import me.dmdev.rxpm.widget.dialogControl
import ru.app.incredible.pets.R
import ru.app.incredible.pets.domain.*
import ru.app.incredible.pets.system.ResourceHelper
import ru.app.incredible.pets.ui.common.BasePm

class PetsPm(
    private val resourceHelper: ResourceHelper,
    private val downloadImageInteractor: DownloadImageInteractor,
    private val randomDogInteractor: RandomDogInteractor,
    private val randomCatInteractor: RandomCatInteractor,
    private val getDownloadStateInteractor: GetDownloadStateInteractor,
    private val removeImageInteractor: RemoveImageInteractor
) : BasePm() {

    private var countClicks = 1

    val messageDialog = dialogControl<String, Unit>()

    val dogImageUrl = state<String>()

    val imageDownloadStatus = state(ImageDownloadState.IDLE)

    val progress = state(false)
    val updateImageButtonEnabled = state<Boolean>()

    val updateImageButtonClicks = action<Unit>()
    val toolbarItemButtonClicks = action<ToolbarItem>()

    override fun onCreate() {
        super.onCreate()

        randomDog()
            .doOnError { showErrorMessage(it, resourceHelper) }
            .subscribe({ dogImageUrl.consumer.accept(it.dogImageUrl) }, {})
            .untilDestroy()

        dogImageUrl.observable
            .flatMap { getDownloadStateInteractor.execute(Pet(0), it) }
            .subscribe(imageDownloadStatus.consumer)
            .untilDestroy()

        updateImageButtonClicks.observable
            .map { countClicks++ }
            .flatMapSingle { countClicks ->
                if (countClicks % 2 == 0) {
                    randomDog()
                } else {
                    randomCat()
                }
            }
            .doOnError { showErrorMessage(it, resourceHelper) }
            .retry()
            .subscribe { pet ->
                when (pet) {
                    is Dog -> dogImageUrl.consumer.accept(pet.dogImageUrl)
                    is Cat -> dogImageUrl.consumer.accept(pet.catImageUrl)
                }
            }
            .untilDestroy()

        toolbarItemButtonClicks.observable
            .filter { it == ToolbarItem.DOWNLOAD }
            .flatMapCompletable { downloadImageInteractor.execute(Pet(0), dogImageUrl.value) }
            .doOnError { showErrorMessage(it, resourceHelper) }
            .retry()
            .subscribe()
            .untilDestroy()

        toolbarItemButtonClicks.observable
            .filter { it == ToolbarItem.REMOVE }
            .flatMapSingle { removeImageInteractor.execute(dogImageUrl.value) }
            .retry()
            .subscribe {
                if (it) {
                    showMessage(R.string.message_image_removed)
                } else {
                    showMessage(R.string.message_image_not_found)
                }
                imageDownloadStatus.consumer.accept(ImageDownloadState.IDLE)
            }
            .untilDestroy()

    }

    private fun randomDog(): Single<Dog> {
        return randomDogInteractor.execute()
            .doOnSubscribe { updateImageButtonEnabled.consumer.accept(false) }
            .doFinally { updateImageButtonEnabled.consumer.accept(true) }
            .bindProgress(progress.consumer)
    }

    private fun randomCat(): Single<Cat> {
        return randomCatInteractor.execute()
            .doOnSubscribe { updateImageButtonEnabled.consumer.accept(false) }
            .doFinally { updateImageButtonEnabled.consumer.accept(true) }
            .bindProgress(progress.consumer)
    }

    private fun showMessage(message: Int) {
        message.let { messageDialog.show(resourceHelper.getString(it)) }
    }
}