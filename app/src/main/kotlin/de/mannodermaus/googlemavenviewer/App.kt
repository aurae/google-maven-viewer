package de.mannodermaus.googlemavenviewer

import android.app.Application
import de.mannodermaus.googlemavenviewer.controllers.AppModule

open class App : Application() {

    val appComponent = createComponent()

    override fun onCreate() {
        super.onCreate()

        // One-Time Initialization
        appComponent.logInitializer().init(this)
        appComponent.timeInitializer().init(this)
    }

    protected fun createComponent(): AppComponent =
            DaggerAppComponent.builder()
                    .appModule(AppModule(this))
                    .build()
}
