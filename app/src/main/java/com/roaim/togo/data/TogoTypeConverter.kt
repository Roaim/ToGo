package com.roaim.togo.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.roaim.togo.data.model.ToGo

class TogoTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<ToGo>? {
        val listType = object : TypeToken<List<ToGo>>() {}.type
        return Gson().fromJson<List<ToGo>>(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<ToGo>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}