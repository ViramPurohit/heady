package viram.heady.ui.product

import viram.heady.base.BaseCategory
import viram.heady.db.AppDatabase
import viram.heady.model.Product

/**
 * Created by viram on 12/3/2017.
 */
class ProductImpl {

    interface Presenter : BaseCategory.Presenter<View>{
        fun loadProduct(categoryResult: AppDatabase?, categor_id: Int)
    }

    interface View : BaseCategory.View{

        fun updateView(categoryProduct: List<Product>?)
        fun showProgress(boolean: Boolean)
    }
}