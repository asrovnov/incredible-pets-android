package ru.app.incredible.pets.domain.dog

import io.reactivex.Single
import ru.app.incredible.pets.data.gateway.NetworkStateGateway
import ru.app.incredible.pets.data.gateway.RandomDogGateway
import ru.app.incredible.pets.domain.dog.Dog
import ru.app.incredible.pets.domain.exceptions.InternetUnavailableException

class RandomDogInteractor(
    private val networkStateGateway: NetworkStateGateway,
    private val randomDogGateway: RandomDogGateway
) {

    fun execute(): Single<Dog> {
        return networkStateGateway
            .isNetworkEnabled()
            .firstOrError()
            .flatMap {
                if (it) {
                    randomDogGateway.getRandomDog()
                } else {
                    Single.error(InternetUnavailableException())
                }
            }
    }
}