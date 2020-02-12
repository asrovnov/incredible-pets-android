package ru.app.incredible.pets.data.backend.dto

import com.squareup.moshi.Json
import ru.app.incredible.pets.domain.Cat

data class CatDto(
    @Json(name = "file") val file: String
) :DtoToEntityMapper<Cat> {

    override fun toEntity() = Cat(file)
}