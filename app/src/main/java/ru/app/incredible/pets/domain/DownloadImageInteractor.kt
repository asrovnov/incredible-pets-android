package ru.app.incredible.pets.domain

import io.reactivex.Completable
import ru.app.incredible.pets.data.gateway.DownloadImageGateway

class DownloadImageInteractor(
    private val downloadImageGateway: DownloadImageGateway
) {

    fun execute(pet: Pet, imageUrl: String): Completable {
        return downloadImageGateway.startDownload(pet, imageUrl)
    }
}