package viram.heady.ui.product

import viram.heady.model.CategoryResult

/**
 * Created by viram on 12/3/2017.
 */
class ProductPresenter : ProductImpl.Presenter{


    lateinit var view : ProductImpl.View

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    override fun attachView(view: ProductImpl.View) {
        this.view = view
    }

    override fun loadProduct(categoryResult: CategoryResult) {

        if(categoryResult.rankings!!.size > 0){


        }
        view.updateView()
    }
}