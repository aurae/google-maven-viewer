package de.mannodermaus.googlemavenviewer.controllers.api

import com.tickaroo.tikxml.TikXml
import dagger.Module
import dagger.Provides
import de.mannodermaus.googlemavenviewer.App
import de.mannodermaus.googlemavenviewer.utils.AppScoped
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

@Module
class ApiModule {

    @Provides
    @AppScoped
    fun provideHttpClient(app: App): OkHttpClient = OkHttpClient.Builder().apply {
        // Properties
        app.cacheDir?.let { cache(Cache(it, 16 * 1024 * 1024)) }

        // Interceptors
        val logger = HttpLoggingInterceptor { Timber.v(it) }.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        addInterceptor(logger)

    }.build()

    @Provides
    @AppScoped
    fun provideApiManager(httpClient: OkHttpClient, xml: TikXml): ApiManager = RetrofitApiManager(httpClient, xml)
}
