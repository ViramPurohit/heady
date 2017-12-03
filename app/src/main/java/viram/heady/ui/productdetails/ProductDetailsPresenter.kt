package viram.heady.ui.productdetails

/**
 * Created by viram on 12/3/2017.
 */

class ProductDetailsPresenter : ProductDetailsImpl.Presenter{

    lateinit var view : ProductDetailsImpl.View

    override fun subscribe() {

    }

    override fun unSubscribe() {

    }

    override fun attachView(view: ProductDetailsImpl.View) {
        this.view = view
    }

    override fun loadProductDetails() {
        view.updateView()
    }
}