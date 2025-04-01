package com.example.dsw51762_kotlin.db

import androidx.room.TypeConverter
import java.util.Date

class Converters{

    @TypeConverter
    fun fromDate(date : Date) : Long{
        return date.time
    }

    @TypeConverter
    fun toDate(time : Long) : Date{
        return Date(time)
    }
}