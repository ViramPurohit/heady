package viram.heady.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by viram on 12/3/2017.
 */
class ActivityUtil {

    fun addCategoryFragmentToActivity(fragmentManager: FragmentManager,
                                     fragment: Fragment, frameId: Int, fragmentTag: String) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, fragmentTag)
        /*BackStack not required because it's parent Fragment so on backpress app will closed*/
        transaction.commit()

    }

    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment, frameId: Int, fragmentTag: String) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, fragmentTag).addToBackStack(null)
        transaction.commit()
    }


    fun convertStringArrayToString(strArr: ArrayList<Int>, delimiter: String): String {
        val sb = StringBuilder()
        if(strArr != null){
            if(strArr.size > 0){
                for (str : Int in strArr){
                    sb.append(""+str).append(delimiter)
                }
                return sb.substring(0, sb.length - 1)
            }else{
                sb.append("")
                return sb.toString()
            }

        }else{
            sb.append("")
            return sb.toString()
        }


    }
}