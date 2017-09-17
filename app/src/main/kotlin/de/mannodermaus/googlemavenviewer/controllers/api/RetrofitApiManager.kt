package de.mannodermaus.googlemavenviewer.controllers.api

import com.annimon.stream.ComparatorCompat
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import com.tickaroo.tikxml.typeadapter.TypeAdapter
import de.mannodermaus.googlemavenviewer.models.ArtifactDetails
import de.mannodermaus.googlemavenviewer.models.ArtifactId
import de.mannodermaus.googlemavenviewer.models.ArtifactSummary
import de.mannodermaus.googlemavenviewer.models.GroupId
import de.mannodermaus.googlemavenviewer.models.GroupSummary
import de.mannodermaus.googlemavenviewer.models.Version
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import org.threeten.bp.Instant
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET

/* Extensions */

private fun GroupId.toUrlPath() = this.replace('.', '/')

/* Classes */

class RetrofitApiManager(httpClient: OkHttpClient, serializer: TikXml) : ApiManager {

    private val service: RetrofitService = Retrofit.Builder()
            .client(httpClient)
            .baseUrl("https://dl.google.com/dl/android/maven2/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(TikXmlConverterFactory.create(serializer))
            .build()
            .create(RetrofitService::class.java)

    override fun listGroups(): Single<List<GroupSummary>> =
            service.listGroups()
                    .flatMapObservable { Observable.fromIterable(it.items) }
                    .map {
                        GroupSummary(
                                groupId = it,
                                // TODO More info
                                name = it.replace('.', ' ').capitalize()
                        )
                    }
                    .toSortedList(ComparatorCompat.comparing<GroupSummary, String> { it.groupId })

    override fun listArtifacts(id: GroupId): Single<List<ArtifactSummary>> =
            service.listGroupArtifacts(id.toUrlPath())
                    .flatMapObservable { Observable.fromIterable(it.items) }
                    .map {
                        ArtifactSummary(
                                groupId = id,
                                artifactId = it.first,
                                latestVersion = it.second
                        )
                    }
                    .toList()

    override fun getArtifact(group: GroupId, artifact: ArtifactId): Single<ArtifactDetails> =
            service.getGroupArtifact("${group.toUrlPath()}.$artifact")
                    .map {
                        ArtifactDetails(
                                groupId = it.groupId,
                                artifactId = it.artifactId,
                                latestVersion = it.latestVersion,
                                releaseVersion = it.releaseVersion,
                                allVersions = it.allVersions,
                                lastUpdated = it.lastUpdated
                        )
                    }
}

private interface RetrofitService {

    @GET("master-index.xml")
    fun listGroups(): Single<Api_GroupIdList>

    @GET("{id}/group-index.xml")
    fun listGroupArtifacts(@retrofit2.http.Path("id", encoded = true) id: GroupId): Single<Api_GroupArtifactIdList>

    @GET("{id}/maven-metadata.xml")
    fun getGroupArtifact(@retrofit2.http.Path("id", encoded = true) id: String): Single<Api_ArtifactDetails>
}

/* Model classes */

/*
 <metadata>
    <com.android.support.constraint/>
    <com.android.databinding/>
    <com.android.support/>
    <com.android.support.test/>
 </metadata>
 */

// The dynamic XML structure of this model
// requires us to mimic TikXML's generated class name.
internal data class Api_GroupIdList(val items: List<GroupId>)

@Suppress("unused")
internal class `Api_GroupIdList$$TypeAdapter` : TypeAdapter<Api_GroupIdList> {
    override fun toXml(writer: XmlWriter, config: TikXmlConfig, value: Api_GroupIdList, overridingXmlElementTagName: String) =
            throw NotImplementedError()

    override fun fromXml(reader: XmlReader, config: TikXmlConfig): Api_GroupIdList {
        val items = mutableListOf<GroupId>()

        while (reader.hasElement()) {
            reader.beginElement()
            items.add(reader.nextElementName())
            reader.endElement()
        }

        return Api_GroupIdList(items.toList())
    }
}

/*
 <android.arch.persistence.room>
    <compiler versions="1.0.0-alpha1,1.0.0-alpha2"/>
    <testing versions="1.0.0-alpha1,1.0.0-alpha2"/>
    ...
 </android.arch.persistence.room>
 */

// The dynamic XML structure of this model
// requires us to mimic TikXML's generated class name.
internal data class Api_GroupArtifactIdList(val items: List<Pair<ArtifactId, Version>>)

@Suppress("unused")
internal class `Api_GroupArtifactIdList$$TypeAdapter` : TypeAdapter<Api_GroupArtifactIdList> {
    override fun toXml(writer: XmlWriter, config: TikXmlConfig, value: Api_GroupArtifactIdList, overridingXmlElementTagName: String) =
            throw NotImplementedError()

    override fun fromXml(reader: XmlReader, config: TikXmlConfig): Api_GroupArtifactIdList {
        val items = mutableListOf<Pair<ArtifactId, Version>>()

        while (reader.hasElement()) {
            reader.beginElement()
            val artifactId = reader.nextElementName()
            while (reader.hasAttribute()) {
                if (reader.nextAttributeName() == "versions") {
                    // Comma-separated list: Find last entry
                    val allVersions = reader.nextAttributeValue()
                    val latestVersion = allVersions.substringAfterLast(",")
                    items.add(artifactId to latestVersion)
                }
            }

            reader.endElement()
        }

        return Api_GroupArtifactIdList(items.toList())
    }
}

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
internal data class Api_ArtifactDetails(
        @PropertyElement(name = "groupId")
        val groupId: GroupId,

        @PropertyElement(name = "artifactId")
        val artifactId: ArtifactId,

        @Path("versioning")
        @PropertyElement(name = "latest")
        val latestVersion: Version,

        @Path("versioning")
        @PropertyElement(name = "release")
        val releaseVersion: Version,

        @Path("versioning/versions")
        @Element(name = "version")
        val allVersions: List<Version>,

        @Path("versioning")
        @PropertyElement(name = "lastUpdated")
        val lastUpdated: Instant
)
