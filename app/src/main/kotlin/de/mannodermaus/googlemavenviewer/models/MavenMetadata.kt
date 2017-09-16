package de.mannodermaus.googlemavenviewer.models

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import org.threeten.bp.Instant

/*
 <metadata>
    <groupId>android.arch.persistence.room</groupId>
    <artifactId>testing</artifactId>
    <versioning>
        <latest>1.0.0-alpha3</latest>
        <release>1.0.0-alpha3</release>
        <versions>
            <version>1.0.0-alpha1</version>
            <version>1.0.0-alpha2</version>
            <version>1.0.0-alpha3</version>
            ...
        </versions>
        <lastUpdated>20170914090807</lastUpdated>
    </versioning>
</metadata>
 */

@Xml(name = "metadata")
data class MavenMetadata(
        @PropertyElement(name = "groupId")
        val groupId: String,

        @PropertyElement(name = "artifactId")
        val artifactId: String,

        @Element(name = "versioning")
        val versions: MavenVersioning)

@Xml(name = "versioning")
data class MavenVersioning(
        @PropertyElement(name = "latest")
        val latest: String,

        @PropertyElement(name = "release")
        val release: String,

        @Path("versions")
        @Element(name = "version")
        val all: List<String>,

        @PropertyElement(name = "lastUpdated")
        val lastUpdated: Instant)
