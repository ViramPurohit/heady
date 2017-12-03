package viram.heady.ui.productdetails

import android.view.View
import viram.heady.base.BaseCategory

/**
 * Created by viram on 12/3/2017.
 */
class ProductDetailsImpl{

    interface Presenter  : BaseCategory.Presenter<View>{
        fun loadProductDetails()
    }

    interface View : BaseCategory.View{

        fun updateView()
    }
}