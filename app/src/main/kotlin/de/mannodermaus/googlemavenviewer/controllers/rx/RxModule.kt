package de.mannodermaus.googlemavenviewer.controllers.rx

import dagger.Module
import dagger.Provides
import de.mannodermaus.googlemavenviewer.utils.AppScoped

@Module
class RxModule {

    @Provides
    @AppScoped
    fun provideSchedulers(): RxSchedulers = AppSchedulers()
}
