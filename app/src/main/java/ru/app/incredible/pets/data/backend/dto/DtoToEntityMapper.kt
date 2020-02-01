package ru.app.incredible.pets.data.backend.dto

interface DtoToEntityMapper<E> {
    fun toEntity(): E
}