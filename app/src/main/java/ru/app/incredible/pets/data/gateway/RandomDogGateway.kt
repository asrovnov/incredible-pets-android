package ru.app.incredible.pets.data.gateway

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.app.incredible.pets.data.backend.ServerApi
import ru.app.incredible.pets.data.backend.mapToEntity
import ru.app.incredible.pets.domain.Dog

class RandomDogGateway(
    private val api: ServerApi
) {

    fun getRandomDog(): Single<Dog> {
        return api.getRandomDog()
            .mapToEntity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}