package ru.app.incredible.pets.domain

import io.reactivex.Single
import ru.app.incredible.pets.data.gateway.DownloadImageGateway

class RemoveImageInteractor(
    private val downloadImageGateway: DownloadImageGateway
) {

    fun execute(imageUrl: String): Single<Boolean> {
        return downloadImageGateway.removeImage(imageUrl)
    }
}