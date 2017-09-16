package de.mannodermaus.googlemavenviewer.controllers.xml

import com.tickaroo.tikxml.TypeConverter
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

internal class InstantTypeConverter : TypeConverter<Instant> {

    private val formatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                    .withZone(ZoneId.of("UTC"))

    override fun read(value: String): Instant = Instant.from(formatter.parse(value))
    override fun write(value: Instant): String = formatter.format(value)
}
