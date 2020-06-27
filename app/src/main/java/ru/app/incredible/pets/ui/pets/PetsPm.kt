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

    val dogImageUrl = state<String> {
        randomPet(0)
            .map { (it as Dog).dogImageUrl }
            .doOnError { showErrorMessage(it, resourceHelper) }
            .toObservable()
    }

    val imageDownloadStatus = state(ImageDownloadState.IDLE) {
        dogImageUrl.observable
            .flatMap { getDownloadStateInteractor.execute(Pet(0), it) }
    }

    val progress = state(false)
    val updateImageButtonEnabled = state<Boolean>()

    val updateImageButtonClicks = action<Unit> {
        this.map { countClicks++ }
            .flatMapSingle { countClicks ->
                randomPet(countClicks)
            }
            .doOnError { showErrorMessage(it, resourceHelper) }
            .doOnNext { pet ->
                when (pet) {
                    is Dog -> dogImageUrl.consumer.accept(pet.dogImageUrl)
                    is Cat -> dogImageUrl.consumer.accept(pet.catImageUrl)
                }
            }
    }

    val downloadButtonClicks = action<Unit> {
        this.flatMap {
            downloadImageInteractor.execute(Pet(0), dogImageUrl.value)
                .toObservable<Unit>()
        }
            .doOnError { showErrorMessage(it, resourceHelper) }
    }

    val removeButtonClicks = action<Unit> {
        this.flatMapSingle {
            removeImageInteractor.execute(dogImageUrl.value)
        }
            .doOnNext {
                if (it) {
                    showMessage(R.string.message_image_removed)
                } else {
                    showMessage(R.string.message_image_not_found)
                }
                imageDownloadStatus.consumer.accept(ImageDownloadState.IDLE)
            }
    }

    private fun randomPet(countClicks: Int): Single<*> {
        return if (countClicks % 2 == 0) {
            randomDogInteractor.execute()
        } else {
            randomCatInteractor.execute()
        }
            .doOnSubscribe { updateImageButtonEnabled.consumer.accept(false) }
            .doFinally { updateImageButtonEnabled.consumer.accept(true) }
            .bindProgress(progress.consumer)
    }

    private fun showMessage(message: Int) {
        message.let { messageDialog.show(resourceHelper.getString(it)) }
    }
}