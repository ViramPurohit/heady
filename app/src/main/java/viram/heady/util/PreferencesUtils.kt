package viram.heady.util

import android.content.Context
import com.google.gson.Gson
import viram.heady.model.CategoryResult
import java.util.*


/**
 * Created by viram on 12/3/2017.
 */
class PreferencesUtils {

    fun saveToPreferences(context: Context,myobject : CategoryResult ){
        val mPrefs = context.getSharedPreferences("heady_pref", Context.MODE_PRIVATE)
        val prefsEditor = mPrefs.edit()
        val gson = Gson()
        val json = gson.toJson(myobject)
        prefsEditor.putString("CategoryResult", json)
        prefsEditor.putBoolean("isCache", true)
        prefsEditor.commit()
    }

    fun getFromPreferences(context: Context) : CategoryResult? {
        var categoryResult : CategoryResult ?= null

        val mPrefs = context.getSharedPreferences("heady_pref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("CategoryResult", null)
        if(json != null){
            categoryResult = gson.fromJson<CategoryResult>(json, CategoryResult::class.java!!) as? CategoryResult

            return categoryResult!!
        }else{
            return null
        }

    }

    fun isRecordWithCache(context: Context) : Boolean? {

        val mPrefs = context.getSharedPreferences("heady_pref", Context.MODE_PRIVATE)
        val isCache = mPrefs.getBoolean("isCache", false)

        return isCache
    }
}