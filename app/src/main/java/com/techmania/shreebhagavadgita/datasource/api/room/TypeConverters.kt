package com.techmania.shreebhagavadgita.datasource.api.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AppTypeConverters {
    @TypeConverter
    fun fromListToString(list: List<String>) : String
    {
        return Gson().toJson(list)
    }
    @TypeConverter
    fun fromStringToList(string: String) : List<String>{
        return Gson().fromJson(string,object : TypeToken<List<String>>(){}.type)
            }


    
}