package de.mannodermaus.googlemavenviewer.models

import org.threeten.bp.Instant

data class GroupSummary(
        val groupId: GroupId,
        val name: String)

data class ArtifactSummary(
        val groupId: GroupId,
        val artifactId: ArtifactId,
        val latestVersion: Version)

data class ArtifactDetails(
        val groupId: GroupId,
        val artifactId: ArtifactId,
        val latestVersion: Version,
        val releaseVersion: Version,
        val allVersions: List<Version>,
        val lastUpdated: Instant)
