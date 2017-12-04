package viram.heady.ui.productdetails

import android.content.Context
import viram.heady.model.Variant

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

    override fun loadProductDetails(context: Context, variants: List<Variant>?) {

        if (variants != null) {

            /*Remove duplicate size from list*/
            var variantlist = ArrayList<Variant>()

            variantlist.addAll(variants!!)

            try {
                var map = LinkedHashMap<Double, Variant>()
                for (variant : Variant in variants!!){
                    map.put(variant.size!!,variant)
                }
                variantlist.clear()

                variantlist.addAll(map.values)

                if(variantlist.size > 0){
                    if(variants[0].size != null){
                        view.showSize(true)
                        view.updateSize(variantlist)

                    }else{
                        view.updateColors(variantlist)
                        view.showSize(false)
                    }
                }

            } catch (e: Exception) {

                view.showSize(false)
            }


        }
        view.updateView()
    }
}