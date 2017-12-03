package viram.heady.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by viram on 12/3/2017.
 */
class ActivityUtil {

    public fun addFragmentToActivity(fragmentManager: FragmentManager,
                                     fragment: Fragment, frameId: Int, fragmentTag: String) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, fragmentTag).addToBackStack("")
        transaction.commit()
    }
}