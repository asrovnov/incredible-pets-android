package ru.app.incredible.pets.ui.dog

import io.reactivex.Single
import me.dmdev.rxpm.action
import me.dmdev.rxpm.bindProgress
import me.dmdev.rxpm.state
import ru.app.incredible.pets.domain.Dog
import ru.app.incredible.pets.domain.RandomDogInteractor
import ru.app.incredible.pets.ui.common.BasePm
import timber.log.Timber

class DogPm(
    private val randomDogInteractor: RandomDogInteractor
) : BasePm() {

    val dogImageUrl = state<String>()

    val progressDog = state(false)
    val updateImageButtonEnabled = state(true)

    val updateImageButtonClicks = action<Unit>()

    override fun onCreate() {
        super.onCreate()

        randomDog()
            .subscribe(
                {
                    dogImageUrl.consumer.accept(it?.dogImageUrl)
                },
                {
                    Timber.d("!! error: $it")
                }
            )
            .untilDestroy()

        updateImageButtonClicks.observable
            .flatMapSingle {
                randomDog()
            }
            .subscribe(
                {
                    dogImageUrl.consumer.accept(it?.dogImageUrl)
                },
                {
                    Timber.tag("!!").d("error: $it")
                }
            )
            .untilDestroy()
    }

    private fun randomDog(): Single<Dog> {
        return randomDogInteractor.execute()
            .doOnSubscribe { updateImageButtonEnabled.consumer.accept(false) }
            .doFinally { updateImageButtonEnabled.consumer.accept(true) }
            .bindProgress(progressDog.consumer)
    }
}