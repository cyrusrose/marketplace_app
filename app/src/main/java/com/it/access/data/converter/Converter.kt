package com.it.access.data.converter

import androidx.room.TypeConverter
import java.math.BigDecimal

const val scale = 2

class Converter {
    @TypeConverter
    fun fromString(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it).setScale(scale) }
    }

    @TypeConverter
    fun toString(date: BigDecimal?): String? {
        return date?.setScale(scale)?.toString()
    }
}