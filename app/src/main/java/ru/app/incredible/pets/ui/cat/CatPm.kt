package ru.app.incredible.pets.ui.cat

import io.reactivex.Single
import me.dmdev.rxpm.action
import me.dmdev.rxpm.bindProgress
import me.dmdev.rxpm.state
import ru.app.incredible.pets.domain.Cat
import ru.app.incredible.pets.domain.RandomCatInteractor
import ru.app.incredible.pets.ui.common.BasePm
import timber.log.Timber

class CatPm(
    private val randomCatInteractor: RandomCatInteractor
) : BasePm() {

    val catImageUrl = state<String>()

    val progressCat = state(false)
    val updateImageButtonEnabled = state(true)

    val updateImageButtonClicks = action<Unit>()

    override fun onCreate() {
        super.onCreate()

        randomCat()
            .subscribe(
                {
                    catImageUrl.consumer.accept(it.catImageUrl)
                },
                {
                    Timber.d("!! error: $it")
                }
            )
            .untilDestroy()

        updateImageButtonClicks.observable
            .flatMapSingle {
                randomCat()
            }
            .subscribe(
                {
                    catImageUrl.consumer.accept(it.catImageUrl)
                },
                {
                    Timber.tag("!!").d("error: $it")
                }
            )
            .untilDestroy()
    }

    private fun randomCat(): Single<Cat> {
        return randomCatInteractor.execute()
            .doOnSubscribe { updateImageButtonEnabled.consumer.accept(false) }
            .doFinally { updateImageButtonEnabled.consumer.accept(true) }
            .bindProgress(progressCat.consumer)
    }
}