package ru.app.incredible.pets.data.backend

import io.reactivex.Single
import ru.app.incredible.pets.data.backend.dto.DtoToEntityMapper

fun <E, D : DtoToEntityMapper<E>> Single<D>.mapToEntity(): Single<E> {
    return this.map { it.toEntity() }
}