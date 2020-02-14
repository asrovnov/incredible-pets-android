package ru.app.incredible.pets.data.gateway

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.app.incredible.pets.data.backend.CatApi
import ru.app.incredible.pets.data.backend.mapToEntity
import ru.app.incredible.pets.domain.Cat

class RandomCatGateway(
    private val api: CatApi
) {

    fun getRandomCat(): Single<Cat> {
        return api.getRandomCat()
            .map { it.first() }
            .mapToEntity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}