package ru.app.incredible.pets.data.gateway

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File

class GetDownloadedImageGateway(
    private val context: Context
) {

    companion object {
        private const val DIR_IMAGE = "images"
    }

    fun getAllImage(): Observable<MutableList<File>> {
        return Observable.fromCallable {
            context.getExternalFilesDir(DIR_IMAGE)
        }
            .map { path ->
                val imageList = mutableListOf<File>()

                path.listFiles()?.forEach {
                    imageList.add(it)
                }
                imageList
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}