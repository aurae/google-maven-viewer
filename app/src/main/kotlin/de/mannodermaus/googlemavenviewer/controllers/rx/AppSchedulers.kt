package de.mannodermaus.googlemavenviewer.controllers.rx

import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

internal class AppSchedulers : RxSchedulers {

    private val subscribeOnScheduler = Schedulers.io()
    private val observeOnScheduler = AndroidSchedulers.mainThread()

    override fun <T> applySingle(): SingleTransformer<T, T> =
            SingleTransformer { upstream ->
                upstream.observeOn(observeOnScheduler).subscribeOn(subscribeOnScheduler)
            }
}