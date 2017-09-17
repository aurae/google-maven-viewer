package de.mannodermaus.googlemavenviewer

import dagger.Component
import de.mannodermaus.googlemavenviewer.controllers.AppModule
import de.mannodermaus.googlemavenviewer.controllers.api.ApiModule
import de.mannodermaus.googlemavenviewer.controllers.log.LogInitializer
import de.mannodermaus.googlemavenviewer.controllers.log.LogModule
import de.mannodermaus.googlemavenviewer.controllers.rx.RxModule
import de.mannodermaus.googlemavenviewer.controllers.time.TimeInitializer
import de.mannodermaus.googlemavenviewer.controllers.time.TimeModule
import de.mannodermaus.googlemavenviewer.controllers.xml.XmlModule
import de.mannodermaus.googlemavenviewer.views.groups.GroupListPresenter

@Component(
        modules = arrayOf(
                AppModule::class,
                ApiModule::class,
                XmlModule::class,
                RxModule::class,
                LogModule::class,
                TimeModule::class
        )
)
interface AppComponent {

    /* Injection Targets */

    fun inject(presenter: GroupListPresenter)

    /* Shared Dependencies */

    fun logInitializer(): LogInitializer
    fun timeInitializer(): TimeInitializer
}
