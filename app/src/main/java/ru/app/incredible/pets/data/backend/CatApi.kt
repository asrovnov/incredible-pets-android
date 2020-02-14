package ru.app.incredible.pets.data.backend

import io.reactivex.Single
import retrofit2.http.GET
import ru.app.incredible.pets.data.backend.dto.CatDto

interface CatApi {

    @GET("images/search")
    fun getRandomCat(): Single<List<CatDto>>
}