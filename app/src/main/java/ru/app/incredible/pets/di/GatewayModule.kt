package ru.app.incredible.pets.di

import org.koin.dsl.module
import ru.app.incredible.pets.data.gateway.NetworkStateGateway
import ru.app.incredible.pets.data.gateway.RandomCatGateway
import ru.app.incredible.pets.data.gateway.RandomDogGateway

object GatewayModule {

    fun create() = module {
        single { RandomDogGateway(get()) }
        single { RandomCatGateway(get()) }
        single { NetworkStateGateway(get()) }
    }
}