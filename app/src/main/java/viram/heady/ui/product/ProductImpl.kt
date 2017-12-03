package viram.heady.ui.product

import viram.heady.base.BaseCategory

/**
 * Created by viram on 12/3/2017.
 */
class ProductImpl {

    interface Presenter : BaseCategory.Presenter<View>{
        fun loadProduct()
    }

    interface View : BaseCategory.View{

        fun updateView()
    }
}