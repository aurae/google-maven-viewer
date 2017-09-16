package de.mannodermaus.googlemavenviewer.controllers.xml

import com.tickaroo.tikxml.TikXmlConfig
import com.tickaroo.tikxml.XmlReader
import com.tickaroo.tikxml.XmlWriter
import com.tickaroo.tikxml.typeadapter.TypeAdapter

class StringTypeAdapter : TypeAdapter<String> {
    override fun fromXml(reader: XmlReader, config: TikXmlConfig): String = reader.nextTextContent()

    override fun toXml(writer: XmlWriter, config: TikXmlConfig, value: String, overridingXmlElementTagName: String) {
        writer.beginElement(overridingXmlElementTagName)
        writer.textContent(value)
        writer.endElement()
    }
}