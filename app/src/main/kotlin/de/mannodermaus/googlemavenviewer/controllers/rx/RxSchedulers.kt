package de.mannodermaus.googlemavenviewer.controllers.rx

import io.reactivex.SingleTransformer

interface RxSchedulers {

    fun <T> applySingle(): SingleTransformer<T, T>
}