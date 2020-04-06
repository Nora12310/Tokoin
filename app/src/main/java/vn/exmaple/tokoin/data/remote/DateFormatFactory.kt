package vn.exmaple.tokoin.data.remote

import com.squareup.moshi.*
import java.text.SimpleDateFormat
import java.util.*

class DateFormatFactory : JsonAdapter<Date>() {
    private val mFormatter = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    @FromJson
    override fun fromJson(reader: JsonReader): Date? = try {
        val dateAsString = reader.nextString()
        mFormatter.parse(dateAsString)
    } catch (e: Exception) {
        null
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: Date?) {
        value?.let { writer.value(mFormatter.format(it)) }
    }

    companion object {
        const val SERVER_FORMAT = ("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    }
}