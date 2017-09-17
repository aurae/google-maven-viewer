package de.mannodermaus.googlemavenviewer.views.groups

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.ViewGroup
import de.mannodermaus.googlemavenviewer.AppComponent
import de.mannodermaus.googlemavenviewer.R
import de.mannodermaus.googlemavenviewer.models.Adapter
import de.mannodermaus.googlemavenviewer.models.GroupSummary
import de.mannodermaus.googlemavenviewer.models.ViewHolder
import de.mannodermaus.googlemavenviewer.views.MvpActivity
import kotlinx.android.synthetic.main.activity_grouplist.*
import kotlinx.android.synthetic.main.item_group.view.*

class GroupListActivity : MvpActivity<GroupListPresenter, GroupListView>(), GroupListView {

    private val adapter = GroupAdapter()

    override fun createPresenter(component: AppComponent) = GroupListPresenter(component)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grouplist)

        // Setup RecyclerView
        rvGroups.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvGroups.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        getPresenter().loadGroups()
    }

    /* Begin View implementation */

    override fun onLoadGroupsResult(result: LoadGroupsResult) {
        when (result) {
            is LoadGroupsResult.Success -> adapter.setItems(result.groups)
            is LoadGroupsResult.Empty -> adapter.setItems(emptyList())
            is LoadGroupsResult.Error -> result.reason.printStackTrace()
        }
    }
}

/* RecyclerView */

class GroupAdapter : Adapter<GroupSummary>({ parent, _ -> GroupHolder(parent) })
class GroupHolder(parent: ViewGroup) : ViewHolder<GroupSummary>(R.layout.item_group, parent) {
    override fun bind(item: GroupSummary) {
        itemView.tvGroupName.text = item.name
        itemView.tvGroupId.text = item.groupId
    }
}
