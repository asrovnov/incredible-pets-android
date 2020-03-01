package ru.app.incredible.pets.ui.cat

import io.reactivex.Single
import me.dmdev.rxpm.action
import me.dmdev.rxpm.bindProgress
import me.dmdev.rxpm.state
import ru.app.incredible.pets.data.gateway.PetGateway
import ru.app.incredible.pets.domain.Cat
import ru.app.incredible.pets.domain.RandomCatInteractor
import ru.app.incredible.pets.system.ResourceHelper
import ru.app.incredible.pets.ui.common.BasePm

// TODO: (When image download is ready) Make a gallery from this screen
class CatPm(
    private val resourceHelper: ResourceHelper,
    private val randomCatInteractor: RandomCatInteractor,
    petGateway: PetGateway
) : BasePm(petGateway) {

    val catImageUrl = state<String>()

    val progressCat = state(false)
    val updateImageButtonEnabled = state(true)

    val updateImageButtonClicks = action<Unit>()

    override fun onCreate() {
        super.onCreate()

        randomCat()
            .doOnError { showErrorMessage(it, resourceHelper) }
            .subscribe({ catImageUrl.consumer.accept(it.catImageUrl) }, {})
            .untilDestroy()

        updateImageButtonClicks.observable
            .flatMapSingle { randomCat() }
            .doOnError { showErrorMessage(it, resourceHelper) }
            .retry()
            .subscribe({ catImageUrl.consumer.accept(it.catImageUrl) }, {})
            .untilDestroy()
    }

    private fun randomCat(): Single<Cat> {
        return randomCatInteractor.execute()
            .doOnSubscribe { updateImageButtonEnabled.consumer.accept(false) }
            .doFinally { updateImageButtonEnabled.consumer.accept(true) }
            .bindProgress(progressCat.consumer)
    }
}