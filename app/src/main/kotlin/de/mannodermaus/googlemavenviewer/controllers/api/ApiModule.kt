package de.mannodermaus.googlemavenviewer.controllers.api

import com.tickaroo.tikxml.TikXml
import dagger.Module
import dagger.Provides
import de.mannodermaus.googlemavenviewer.utils.AppScoped

@Module
class ApiModule {

    @Provides @AppScoped
    fun provideApiManager(xml: TikXml): ApiManager = RetrofitApiManager(xml)
}
