package de.mannodermaus.googlemavenviewer.utils

import android.content.Context
import android.support.v4.app.Fragment
import de.mannodermaus.googlemavenviewer.App
import de.mannodermaus.googlemavenviewer.AppComponent
import de.mannodermaus.googlemavenviewer.controllers.rx.RxSchedulers
import io.reactivex.Single

/* Android */

val Context.appComponent: AppComponent
    get() = (this.applicationContext as App).appComponent

val Fragment.appComponent: AppComponent
    get() = activity.appComponent

val android.app.Fragment.appComponent: AppComponent
    get() = activity.appComponent

/* RxJava */

fun <T> Single<T>.async(schedulers: RxSchedulers): Single<T> =
        this.compose(schedulers.applySingle())
