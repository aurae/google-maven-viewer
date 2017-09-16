package de.mannodermaus.googlemavenviewer.controllers.api

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import de.mannodermaus.googlemavenviewer.models.ArtifactId
import de.mannodermaus.googlemavenviewer.models.MavenMetadata
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET

class RetrofitApiManager(serializer: TikXml) : ApiManager {

    private val service: RetrofitService = Retrofit.Builder()
            .baseUrl("https://dl.google.com/dl/android/maven2/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(TikXmlConverterFactory.create(serializer))
            .build()
            .create(RetrofitService::class.java)
}

interface RetrofitService {

    @GET("master-index.xml")
    fun listArtifacts(): Single<List<String>> /* todo return type */

    @GET("TODO_howtodynamicallymap...")
    fun getArtifact(artifactId: ArtifactId): Single<MavenMetadata>
}