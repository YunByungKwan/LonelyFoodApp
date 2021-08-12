package org.ybk.fooddiaryapp.data.model.etc

import androidx.room.TypeConverter
import com.google.gson.Gson
import org.ybk.fooddiaryapp.data.model.diary.FoodImage

class DataConverter {

    @TypeConverter
    fun listToJson(list: List<FoodImage>?) = Gson().toJson(list)

    @TypeConverter
    fun jsonToList(str: String) = Gson().fromJson(str, Array<FoodImage>::class.java).toList()

}