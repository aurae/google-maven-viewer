package de.mannodermaus.googlemavenviewer.controllers.api

import de.mannodermaus.googlemavenviewer.models.ArtifactDetails
import de.mannodermaus.googlemavenviewer.models.ArtifactId
import de.mannodermaus.googlemavenviewer.models.ArtifactSummary
import de.mannodermaus.googlemavenviewer.models.GroupId
import de.mannodermaus.googlemavenviewer.models.GroupSummary
import io.reactivex.Single

interface ApiManager {
    fun listGroups(): Single<List<GroupSummary>>
    fun listArtifacts(id: GroupId): Single<List<ArtifactSummary>>
    fun getArtifact(group: GroupId, artifact: ArtifactId): Single<ArtifactDetails>
}
