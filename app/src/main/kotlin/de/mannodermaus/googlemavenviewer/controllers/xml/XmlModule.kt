package de.mannodermaus.googlemavenviewer.controllers.xml

import com.tickaroo.tikxml.TikXml
import dagger.Module
import dagger.Provides
import de.mannodermaus.googlemavenviewer.utils.AppScoped
import org.threeten.bp.Instant

@Module
class XmlModule {

    @Provides @AppScoped
    fun provideXml(): TikXml = TikXml.Builder()
            .addTypeConverter(Instant::class.java, InstantTypeConverter())
            .addTypeAdapter(String::class.java, StringTypeAdapter())
            .build()
}
