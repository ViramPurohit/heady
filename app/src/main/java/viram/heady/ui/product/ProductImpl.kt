package viram.heady.ui.product

import viram.heady.base.BaseCategory
import viram.heady.model.CategoryResult

/**
 * Created by viram on 12/3/2017.
 */
class ProductImpl {

    interface Presenter : BaseCategory.Presenter<View>{
        fun loadProduct(categoryResult: CategoryResult)
    }

    interface View : BaseCategory.View{

        fun updateView()
    }
}