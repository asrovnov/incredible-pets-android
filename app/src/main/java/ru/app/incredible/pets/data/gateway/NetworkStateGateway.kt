package ru.app.incredible.pets.data.gateway

import android.content.Context
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NetworkStateGateway(private val context: Context) {

    fun isNetworkEnabled(): Observable<Boolean> {
        return ReactiveNetwork
            .observeNetworkConnectivity(context)
            .subscribeOn(Schedulers.io())
            .map { it.available() && it.detailedState() == NetworkInfo.DetailedState.CONNECTED }
            .observeOn(AndroidSchedulers.mainThread())
    }
}