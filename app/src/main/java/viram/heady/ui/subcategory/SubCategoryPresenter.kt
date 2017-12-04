package viram.heady.ui.subcategory

import viram.heady.model.Category
import kotlin.collections.ArrayList

/**
 * Created by Viram Purohit on 12/4/2017.
 */
class SubCategoryPresenter : SubCategoryImpl.Presenter{

    lateinit var mView : SubCategoryImpl.View

    override fun getSubCategory(categories: Category, categoryResult: ArrayList<Category>) {
        var categorylist = ArrayList<Category>()
        for (ids: Int in categories.childCategories!!){
            for (category : Category in categoryResult!!){
                if(category.id == ids){
                    categorylist.add(category)
                    break
                }

            }
        }
        mView.updateView(categorylist)
    }

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    override fun attachView(view: SubCategoryImpl.View) {
        mView = view;
    }
}