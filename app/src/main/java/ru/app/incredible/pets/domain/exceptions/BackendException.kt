package ru.app.incredible.pets.domain.exceptions

open class BackendException(
    val code: Int,
    val userMessage: String
) : Exception()