package viram.heady.ui.category

import android.content.Context
import viram.heady.base.BaseCategory
import viram.heady.db.AppDatabase
import viram.heady.model.Category
import viram.heady.model.CategoryResult

/**
 * Created by viram on 12/3/2017.
 */
class CategoryImpl {

    interface Presenter : BaseCategory.Presenter<View>{
        fun loadCategoryAPI(context: Context)
        fun loadCategoryDb(context: Context,appDatabase: AppDatabase?)
        fun addCategoryToDb(categoryDao: AppDatabase?, categoryResult: CategoryResult?)
    }

    interface View : BaseCategory.View{
        fun showProgress(boolean: Boolean)
        fun showError(error : String)
        fun showEmptyView(visible : Boolean)
        fun updateView(categoryResult: CategoryResult?)
        fun updateView_DB(categoryResult: List<Category>)
    }
}