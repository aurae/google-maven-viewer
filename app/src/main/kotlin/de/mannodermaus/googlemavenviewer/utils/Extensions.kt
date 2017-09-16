package de.mannodermaus.googlemavenviewer.utils

import android.content.Context
import de.mannodermaus.googlemavenviewer.App
import de.mannodermaus.googlemavenviewer.AppComponent

val Context.appComponent: AppComponent
    get() = (this.applicationContext as App).appComponent
