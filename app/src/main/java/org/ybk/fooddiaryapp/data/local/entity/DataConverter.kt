package org.ybk.fooddiaryapp.data.local.entity

import androidx.room.TypeConverter
import com.google.gson.Gson

class DataConverter {

    @TypeConverter
    fun listToJson(list: List<FoodImage>?) = Gson().toJson(list)

    @TypeConverter
    fun jsonToList(str: String) = Gson().fromJson(str, Array<FoodImage>::class.java).toList()

}