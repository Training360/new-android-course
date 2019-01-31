package com.example.demodatingapp.util

import androidx.room.TypeConverter

class TypeConverters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun stringListToJson(stringList: Array<String>?): String? {
            return stringList?.joinToString(",")
        }

        @TypeConverter
        @JvmStatic
        fun stringToStringList(string: String?): Array<String>? {
            return string?.let {
                it.split(",").toTypedArray()
            }
        }
    }
}