package de.mannodermaus.googlemavenviewer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tickaroo.tikxml.TikXml
import de.mannodermaus.googlemavenviewer.models.MavenMetadata
import de.mannodermaus.googlemavenviewer.utils.appComponent
import okio.Okio
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var xml: TikXml

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.appComponent.inject(this)

        val source = Okio.buffer(Okio.source(assets.open("test.xml")))
        val parsed = xml.read(source, MavenMetadata::class.java)

        println("Parsed: $parsed")
    }
}
