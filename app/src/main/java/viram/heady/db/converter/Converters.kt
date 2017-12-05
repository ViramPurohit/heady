package viram.heady.db.converter

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import viram.heady.model.Product


/**
 * Created by Viram Purohit on 12/5/2017.
 */
class Converters {

    @TypeConverter
    fun fromProduct(value: String): ArrayList<Product> {
        val listType = object : TypeToken<ArrayList<Product>>() {

        }.type
        return Gson().fromJson(value, listType)
    }
    @TypeConverter
    fun fromArrayLisr(list: ArrayList<Product>): String? {
        val gson = Gson()
        return gson.toJson(list)
    }



}