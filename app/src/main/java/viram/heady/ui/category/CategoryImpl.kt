package viram.heady.ui.category

import android.content.Context
import viram.heady.base.BaseCategory
import viram.heady.model.CategoryResult

/**
 * Created by viram on 12/3/2017.
 */
class CategoryImpl {

    interface Presenter : BaseCategory.Presenter<View>{
        fun loadCategoryAPI(context: Context)
        fun loadCategoryDb(): CategoryResult
    }

    interface View : BaseCategory.View{
        fun showProgress(boolean: Boolean)
        fun showError(error : String)
        fun showEmptyView(visible : Boolean)
        fun updateView(categoryResult: CategoryResult?)
    }
}