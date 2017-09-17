package de.mannodermaus.googlemavenviewer.controllers.log

import dagger.Module
import dagger.Provides
import de.mannodermaus.googlemavenviewer.App
import de.mannodermaus.googlemavenviewer.utils.AppScoped
import timber.log.Timber

@Module
class LogModule {

    @Provides @AppScoped
    fun provideLogInitializer(): LogInitializer = object : LogInitializer {
        override fun init(app: App) {
            // TODO Crashlytics
            // Timber.plant(CrashlyticsTree())
        }
    }
}
