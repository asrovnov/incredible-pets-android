package ru.app.incredible.pets.domain

import io.reactivex.Single
import ru.app.incredible.pets.data.gateway.RandomDogGateway

class RandomDogInteractor(
    private val randomDogGateway: RandomDogGateway
) {
    fun execute(): Single<Dog> {
        return randomDogGateway.getRandomDog()
    }
}