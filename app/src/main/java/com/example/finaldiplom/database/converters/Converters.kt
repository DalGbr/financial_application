package com.example.finaldiplom.database.converters

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): Long? {
        return date?.atStartOfDay()?.toEpochSecond(java.time.ZoneOffset.UTC)
    }

    @TypeConverter
    fun toLocalDate(epochSecond: Long?): LocalDate? {
        return epochSecond?.let {
            LocalDate.ofEpochDay(it / (24 * 60 * 60))
        }
    }
}