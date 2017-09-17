package de.mannodermaus.googlemavenviewer.views.groups

import de.mannodermaus.googlemavenviewer.AppComponent
import de.mannodermaus.googlemavenviewer.controllers.api.ApiManager
import de.mannodermaus.googlemavenviewer.controllers.rx.RxSchedulers
import de.mannodermaus.googlemavenviewer.utils.async
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.rx2.RxTiPresenterDisposableHandler
import javax.inject.Inject

class GroupListPresenter(component: AppComponent) : TiPresenter<GroupListView>() {

    @Inject lateinit var api: ApiManager
    @Inject lateinit var schedulers: RxSchedulers

    private val rx = RxTiPresenterDisposableHandler(this)

    init {
        component.inject(this)
    }

    fun loadGroups() {
        rx.manageViewDisposable(api.listGroups()
                .async(schedulers)
                .map {
                    when (it.size) {
                        0 -> LoadGroupsResult.Empty
                        else -> LoadGroupsResult.Success(it)
                    }
                }
                .onErrorReturn { LoadGroupsResult.Error(it) }
                .subscribe(
                        { view?.onLoadGroupsResult(it) },
                        { it.printStackTrace() }
                )
        )
    }
}