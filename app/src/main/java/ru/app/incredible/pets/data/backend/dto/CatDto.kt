package ru.app.incredible.pets.data.backend.dto

import com.squareup.moshi.Json
import ru.app.incredible.pets.domain.Cat

data class CatDto(
    @Json(name = "url") val url: String
) :DtoToEntityMapper<Cat> {

    override fun toEntity() = Cat(url)
}