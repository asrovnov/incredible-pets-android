package ru.app.incredible.pets.data.backend

interface NetworkConfig {
    val baseUrl: String
}

class DogNetworkConfig : NetworkConfig {
    override val baseUrl = "https://dog.ceo/api/"
}