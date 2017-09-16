package de.mannodermaus.googlemavenviewer.controllers

import dagger.Module
import dagger.Provides
import de.mannodermaus.googlemavenviewer.App
import de.mannodermaus.googlemavenviewer.utils.AppScoped

@Module
class AppModule(private val app: App) {

    @Provides @AppScoped
    fun provideApp(): App = app
}
