package vn.exmaple.tokoin.data.local.converter

import androidx.room.TypeConverter
import org.akd.support.helper.JsonGenerator
import vn.exmaple.tokoin.model.Source
import java.util.*

open class RoomConverter {

    @TypeConverter
    fun dateToLong(date: Date): Long = date.time

    @TypeConverter
    fun longToDate(time: Long): Date {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        return calendar.time
    }

    @TypeConverter
    fun sourceToJson(source: Source): String? = JsonGenerator.toJson(source)

    @TypeConverter
    fun jsonToSource(json: String): Source? = JsonGenerator.fromJson(json, Source::class.java)
}