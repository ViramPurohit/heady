package viram.heady.ui.productdetails

import android.content.Context
import viram.heady.base.BaseCategory
import viram.heady.model.Variant

/**
 * Created by viram on 12/3/2017.
 */
class ProductDetailsImpl{

    interface Presenter  : BaseCategory.Presenter<View>{
        fun loadProductDetails(context: Context,variants: List<Variant>?)
    }

    interface View : BaseCategory.View{

        fun updateView()
        fun showSize(boolean: Boolean)
        fun updateSize(variants: ArrayList<Variant>)
        fun updateColors(variants: ArrayList<Variant>)
    }
}