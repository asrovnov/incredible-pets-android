package ru.app.incredible.pets.data.gateway

import android.content.Context
import com.jakewharton.rxrelay2.BehaviorRelay
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadSampleListener
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.model.FileDownloadStatus
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.app.incredible.pets.domain.ImageDownloadState
import ru.app.incredible.pets.domain.Pet
import ru.app.incredible.pets.domain.exceptions.InternetUnavailableException
import java.io.File

class DownloadImageGateway(
    private val context: Context,
    private val networkStateGateway: NetworkStateGateway
) {

    companion object {
        private const val DIR_IMAGE = "images"
        private const val BOTTOM_SPACE = "_"
    }

    private val downloadImageIds = mutableMapOf<Pet, Int>()
    private val fileDownloader = FileDownloader.getImpl()

    private val imageDownloadStates: BehaviorRelay<MutableMap<Pet, ImageDownloadState>> =
        BehaviorRelay.createDefault(
            mutableMapOf()
        )

    fun getDownloadState(pet: Pet, imageUrl: String): Observable<ImageDownloadState> {
        return imageDownloadStates.hide()
            .map { it[pet] ?: ImageDownloadState.IDLE }
            .doOnSubscribe { checkLoadImage(pet, imageUrl) }
    }

    fun startDownload(pet: Pet, imageUrl: String): Completable {
        return networkStateGateway
            .isNetworkEnabled()
            .firstOrError()
            .flatMapCompletable {
                if (it) {
                    Completable.fromAction {
                        imageDownload(pet, imageUrl)
                    }
                } else {
                    Completable.error(InternetUnavailableException())
                }
            }
    }

    fun removeImage(imageUrl: String): Single<Boolean> {
        return Single
            .fromCallable { getImageFile(imageUrl).delete() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun imageDownload(pet: Pet, imageUrl: String) {
        val downloadStart = fileDownloader.create(imageUrl)
            .setPath(getImageFile(imageUrl).toString())
            .setListener(object : FileDownloadSampleListener() {
                override fun started(task: BaseDownloadTask?) {
                    updateDownloadState(pet, ImageDownloadState.PROGRESS)
                }

                override fun completed(task: BaseDownloadTask?) {
                    updateDownloadState(pet, ImageDownloadState.FINISHED)
                }

                override fun error(task: BaseDownloadTask?, e: Throwable?) {
                    updateDownloadState(pet, ImageDownloadState.ERROR)
                }
            })

        downloadImageIds[pet] = downloadStart.start()
    }

    private fun getImageFile(imageUrl: String): File {
        val imageName =
            "${imageUrl.substringBeforeLast("/").substringAfterLast("/")}$BOTTOM_SPACE${imageUrl.substringAfterLast("/")}"

        return File(context.getExternalFilesDir(DIR_IMAGE), imageName)
    }

    private fun checkLoadImage(pet: Pet, imageUrl: String) {
        val downloadStatus = downloadImageIds[pet]?.let {
            fileDownloader.getStatusIgnoreCompleted(it)
        }

        when {
            getImageFile(imageUrl).exists() -> updateDownloadState(pet, ImageDownloadState.FINISHED)
            downloadStatus == FileDownloadStatus.progress -> updateDownloadState(
                pet,
                ImageDownloadState.PROGRESS
            )
            else -> updateDownloadState(pet, ImageDownloadState.IDLE)
        }
    }

    private fun updateDownloadState(pet: Pet, state: ImageDownloadState) {
        imageDownloadStates.accept(
            imageDownloadStates.value.apply {
                this!![pet] = state
            }!!
        )
    }
}