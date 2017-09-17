package de.mannodermaus.googlemavenviewer.views.groups

import de.mannodermaus.googlemavenviewer.models.GroupSummary

sealed class LoadGroupsResult {
    class Success(val groups: List<GroupSummary>) : LoadGroupsResult()
    class Error(val reason: Throwable) : LoadGroupsResult()
    object Empty : LoadGroupsResult()
}