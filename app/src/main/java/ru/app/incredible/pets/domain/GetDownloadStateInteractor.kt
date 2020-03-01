package ru.app.incredible.pets.domain

import io.reactivex.Observable
import ru.app.incredible.pets.data.gateway.DownloadImageGateway

class GetDownloadStateInteractor(
    private val downloadImageGateway: DownloadImageGateway
) {

    fun execute(pet: Pet, imageUrl: String): Observable<ImageDownloadState> {
        return downloadImageGateway.getDownloadState(pet, imageUrl)
    }
}