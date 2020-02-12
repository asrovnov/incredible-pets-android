package ru.app.incredible.pets.domain

import ru.app.incredible.pets.data.gateway.RandomCatGateway

class RandomCatInteractor(
    private val randomCatGateway: RandomCatGateway
) {

    fun execute() = randomCatGateway.getRandomCat()
}