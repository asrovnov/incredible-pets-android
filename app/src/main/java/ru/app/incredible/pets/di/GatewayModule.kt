package ru.app.incredible.pets.di

import org.koin.dsl.module
import ru.app.incredible.pets.data.gateway.*

object GatewayModule {

    fun create() = module {
        single { RandomDogGateway(get()) }
        single { RandomCatGateway(get()) }
        single { NetworkStateGateway(get()) }
        single { DownloadImageGateway(get(), get()) }
        single { PetGateway() }
        single { GetDownloadedImageGateway(get()) }
    }
}