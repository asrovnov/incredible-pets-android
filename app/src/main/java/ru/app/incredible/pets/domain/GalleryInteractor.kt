package ru.app.incredible.pets.domain

import io.reactivex.Observable
import ru.app.incredible.pets.data.gateway.DownloadImageGateway
import ru.app.incredible.pets.data.gateway.GetDownloadedImageGateway
import java.io.File

class GalleryInteractor(
    private val downloadImageGateway: DownloadImageGateway,
    private val getDownloadedImageGateway: GetDownloadedImageGateway
) {

    fun execute(): Observable<MutableList<File>> {
        return getDownloadedImageGateway.getAllImage()
            .concatMap { imageList ->
                downloadImageGateway.getImageDownloaded()
                    .map { file ->
                        if (file.exists()) {
                            imageList.add(file)
                        } else if (file.name == DownloadImageGateway.FILE_DELETED) {
                            imageList.removeAt(imageList.lastIndex)
                        }
                        imageList
                    }
            }
    }
}