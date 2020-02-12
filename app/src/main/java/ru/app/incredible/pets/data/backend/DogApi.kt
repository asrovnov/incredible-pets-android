package ru.app.incredible.pets.data.backend

import io.reactivex.Single
import retrofit2.http.GET
import ru.app.incredible.pets.data.backend.dto.DogDto

interface DogApi {

    @GET("breeds/image/random")
    fun getRandomDog(): Single<DogDto>
}