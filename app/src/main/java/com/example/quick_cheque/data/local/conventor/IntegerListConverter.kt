package com.example.quick_cheque.data.local.conventor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntegerListConverter {
    @TypeConverter
    fun fromIntegerList(integerList: List<Int>): String {
        return Gson().toJson(integerList)
    }

    @TypeConverter
    fun toIntegerList(integerListString: String): List<Int> {
        val integerListType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(integerListString, integerListType)
    }
}