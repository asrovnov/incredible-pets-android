package ru.app.incredible.pets.domain

import ru.app.incredible.pets.data.gateway.RandomDogGateway

class RandomDogInteractor(
    private val randomDogGateway: RandomDogGateway
) {

    fun execute() = randomDogGateway.getRandomDog()
}