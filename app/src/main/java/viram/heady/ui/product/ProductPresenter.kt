package viram.heady.ui.product

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import viram.heady.db.AppDatabase
import viram.heady.model.Product

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

    override fun loadProduct(appDatabase: AppDatabase?, categor_id: Int) {

        Observable.just(1)
                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    var categoryProduct: List<Product> =  appDatabase!!.productDao().getCategoryProduct(categor_id)

                    view.updateView(categoryProduct )

                }, {

                    throwable -> throwable.printStackTrace()
                    Log.e(" TAG ", "onInsertProduct Error--------");
                })

    }
}