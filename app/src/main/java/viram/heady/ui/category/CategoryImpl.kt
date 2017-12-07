package viram.heady.ui.category

import android.content.Context
import viram.heady.base.BaseCategory
import viram.heady.db.AppDatabase
import viram.heady.model.Category
import viram.heady.model.CategoryResult
import viram.heady.model.Product
import viram.heady.model.Ranking

/**
 * Created by viram on 12/3/2017.
 */
class CategoryImpl {

    interface Presenter : BaseCategory.Presenter<View>{
        fun loadCategoryCount(appDatabase: AppDatabase?)
        fun loadCategoryAPI(context: Context)
        fun loadCategoryDb(context: Context,appDatabase: AppDatabase?)
        fun addCategoryToDb(categoryDao: AppDatabase?, categoryResult: CategoryResult?)

       fun getRankedProduct(appDatabase: AppDatabase?, ranking: String)
       fun getSearchedProduct(appDatabase: AppDatabase?, product_name: String)
    }

    interface View : BaseCategory.View{
        fun showProgress(boolean: Boolean)
        fun showError(error : String)
        fun showEmptyView(visible : Boolean)
        fun showCategoryCount(count : Int)

        fun updateView_Rank(ranking: List<Ranking>)
        fun updateView_Local(categoryResult: List<Category>)
        fun updateView_DB(categoryResult: CategoryResult?)
        fun updateView_Sorting(product : List<Product>,order : Int)
    }
}