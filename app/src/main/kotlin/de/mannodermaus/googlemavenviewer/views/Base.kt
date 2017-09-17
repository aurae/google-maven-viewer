package de.mannodermaus.googlemavenviewer.views

import com.pascalwelsch.compositeandroid.activity.CompositeActivity
import com.pascalwelsch.compositeandroid.fragment.CompositeFragment
import de.mannodermaus.googlemavenviewer.AppComponent
import de.mannodermaus.googlemavenviewer.utils.appComponent
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.TiView
import net.grandcentrix.thirtyinch.plugin.TiActivityPlugin
import net.grandcentrix.thirtyinch.plugin.TiFragmentPlugin

/* General Foundation */

abstract class BaseActivity : CompositeActivity()
abstract class BaseFragment : CompositeFragment()

/* MVP Foundation */

abstract class MvpActivity<out P : TiPresenter<V>, V : TiView> : BaseActivity() {

    private lateinit var presenter: P

    init {
        @Suppress("LeakingThis")
        addPlugin(TiActivityPlugin<P, V> {
            presenter = createPresenter(appComponent)
            presenter
        })
    }

    abstract fun createPresenter(component: AppComponent): P
    protected fun getPresenter(): P = presenter
}

abstract class MvpFragment<out P: TiPresenter<V>, V : TiView> : BaseFragment() {

    private lateinit var presenter: P

    init {
        @Suppress("LeakingThis")
        addPlugin(TiFragmentPlugin<P, V> {
            presenter = createPresenter(appComponent)
            presenter
        })
    }

    abstract fun createPresenter(component: AppComponent): P
    protected fun getPresenter(): P = presenter
}
