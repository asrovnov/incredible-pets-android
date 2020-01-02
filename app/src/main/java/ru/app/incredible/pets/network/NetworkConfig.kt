package ru.app.incredible.pets.network

interface NetworkConfig {
    val baseUrl: String
}

class DogNetworkConfig : NetworkConfig {
    override val baseUrl = "https://dog.ceo/"
}