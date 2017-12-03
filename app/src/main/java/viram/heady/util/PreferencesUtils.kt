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
        prefsEditor.putString("MyObject", json)
        prefsEditor.commit()
    }

    fun getFromPreferences(context: Context) : CategoryResult {
        val mPrefs = context.getSharedPreferences("heady_pref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = mPrefs.getString("MyObject", "")
        val obj = gson.fromJson<CategoryResult>(json, CategoryResult::class.java!!)

        return obj
    }


}