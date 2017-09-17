package de.mannodermaus.googlemavenviewer.views.groups

import net.grandcentrix.thirtyinch.TiView

interface GroupListView : TiView {
    fun onLoadGroupsResult(result: LoadGroupsResult)
}
