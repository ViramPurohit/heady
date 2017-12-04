package viram.heady.ui.subcategory

import viram.heady.base.BaseCategory
import viram.heady.model.Category

/**
 * Created by viram on 12/3/2017.
 */
class SubCategoryImpl {

    interface Presenter : BaseCategory.Presenter<View>{
        fun getSubCategory(childCategories: Category, categoryResult: java.util.ArrayList<Category>)
    }

    interface View : BaseCategory.View{

        fun updateView(categoryResult: ArrayList<Category>)
    }
}