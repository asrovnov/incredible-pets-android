package ru.app.incredible.pets.data.backend.dto

import com.squareup.moshi.Json
import ru.app.incredible.pets.domain.Dog

data class DogDto(
    @Json(name = "message") val message: String,
    @Json(name = "status") val status: String
) : DtoToEntityMapper<Dog> {

    override fun toEntity() = Dog(message, status)
}