package com.example.deadliner.data

import androidx.room.TypeConverter
import java.util.*

/**
 * Converters
 *
 * @constructor Create empty Converters
 */
class Converters {
    @TypeConverter
            /**
             * From timestamp
             *
             * @param value
             * @return
             */
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    /**
     * Date to timestamp
     *
     * @param date
     * @return
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}