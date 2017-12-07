package viram.heady.ui.product

import android.os.AsyncTask
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import viram.heady.db.AppDatabase
import viram.heady.model.Product
import viram.heady.model.Tax

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

    override fun loadProduct(appDatabase: AppDatabase?, category_id: Int) {
        view.showProgress(true)
        object : AsyncTask<Void, Void, List<Product>>() {
            override// this runs on another thread
            fun doInBackground(vararg params: Void): List<Product>? {

                var categoryProduct_temp: List<Product> =
                        appDatabase!!.productDao().getCategoryProduct(category_id)

                var categoryProduct = ArrayList<Product>()
                for (product_ in categoryProduct_temp){
                    product_.variants = appDatabase!!.variantDao().getVariant(product_.id!!)
                    product_.tax = appDatabase!!.taxDao().getTax(product_.id!!)

                    categoryProduct.add(product_)
                }
                return categoryProduct
            }

            override fun onPostExecute(result: List<Product>?) {
                super.onPostExecute(result)
                view.updateView(result)
                view.showProgress(false)
            }


        }.execute() // call execute, NOT doInBackGround

    }

//    fun loadVariant(appDatabase: AppDatabase?, product_id: Int): List<Variant>? {
//        var variant_list : List<Variant>
//        Observable.just(1)
//                .observeOn(Schedulers.io())
////                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe({ item ->
//                    variant_list = appDatabase!!.variantDao().getVariant(product_id)
//
//                }, {
//
//                    throwable ->
//                    throwable.printStackTrace()
//                    Log.e(" TAG ", "onInsertProduct Error--------");
//                })
//        return variant_list
//
//    }
    fun loadTax(appDatabase: AppDatabase?, product_id: Int): Tax? {

        Observable.just(1)
                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    var categoryProduct: List<Product> =  appDatabase!!.productDao().getCategoryProduct(product_id)

                    for (product_ in categoryProduct){

                    }
                    view.updateView(categoryProduct )

                }, {

                    throwable -> throwable.printStackTrace()
                    Log.e(" TAG ", "onInsertProduct Error--------");
                })

    return null
    }
}