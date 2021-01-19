package ru.app.incredible.pets.domain.cat

import io.reactivex.Single
import ru.app.incredible.pets.data.gateway.NetworkStateGateway
import ru.app.incredible.pets.data.gateway.RandomCatGateway
import ru.app.incredible.pets.domain.cat.Cat
import ru.app.incredible.pets.domain.exceptions.InternetUnavailableException

class RandomCatInteractor(
    private val networkStateGateway: NetworkStateGateway,
    private val randomCatGateway: RandomCatGateway
) {

    fun execute(): Single<Cat> {
        return networkStateGateway
            .isNetworkEnabled()
            .firstOrError()
            .flatMap {
                if (it) {
                    randomCatGateway.getRandomCat()
                } else {
                    Single.error(InternetUnavailableException())
                }
            }
    }
}