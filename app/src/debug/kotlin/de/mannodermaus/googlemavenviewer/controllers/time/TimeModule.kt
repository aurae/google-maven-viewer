package de.mannodermaus.googlemavenviewer.controllers.time

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.Module
import dagger.Provides
import de.mannodermaus.googlemavenviewer.App
import de.mannodermaus.googlemavenviewer.utils.AppScoped

@Module
class TimeModule {

    @Provides @AppScoped
    fun provideTimeInitializer(): TimeInitializer = object : TimeInitializer {
        override fun init(app: App) {
            AndroidThreeTen.init(app)
        }
    }
}
