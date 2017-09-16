package de.mannodermaus.googlemavenviewer

import dagger.Component
import de.mannodermaus.googlemavenviewer.controllers.AppModule
import de.mannodermaus.googlemavenviewer.controllers.api.ApiModule
import de.mannodermaus.googlemavenviewer.controllers.xml.XmlModule

@Component(
        modules = arrayOf(
            AppModule::class,
            ApiModule::class,
            XmlModule::class
        )
)
interface AppComponent {
    fun inject(activity: MainActivity)
}
