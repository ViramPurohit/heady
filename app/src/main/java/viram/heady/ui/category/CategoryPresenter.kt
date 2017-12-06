package com.example.viram.heady_test.ui.category

import android.content.Context
import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import viram.heady.api.ApiService
import viram.heady.db.*
import viram.heady.model.*
import viram.heady.ui.category.CategoryImpl
import viram.heady.util.ActivityUtil
import viram.heady.util.PreferencesUtils


/**
 * Created by Viram Purohit on 12/1/2017.
 */
class CategoryPresenter  : CategoryImpl.Presenter{



    private val subscriptions = CompositeDisposable()

    lateinit var view : CategoryImpl.View

    lateinit var context : Context



    fun getCategoryList(observableCategory: Observable<CategoryResult>) {

       var subscribe = observableCategory.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({categoryResults: CategoryResult? ->
                   try {
                       var categoryResult = categoryResults?.categories
                        if(categoryResult!!.size  > 0){
                            var preferencesUtils = PreferencesUtils()
                            preferencesUtils.saveToPreferences(context, categoryResults!!)

                           view.updateView(categoryResults)
                        }else{
                            view.showEmptyView(true)
                        }
                       view.showProgress(false)
                   } catch (e: Exception) {
                       view.showProgress(false)
                   }
               })

        subscriptions.add(subscribe)

//               ApiService().loadCategory(object : GetProductListCallBack {
//            override fun onError(networkError: String) {
//                categoryView.removeWait()
//                categoryView.onFailure(networkError.toString())
//            }
//
//            override fun onSuccess(categoryResult: CategoryResult ) {
//                categoryView.removeWait()
//                categoryView.getCategotyListSuccess(categoryResult)
//            }

    }



    override fun subscribe() {

    }

    override fun unSubscribe() {
        subscriptions.clear()
    }

    override fun attachView(view: CategoryImpl.View) {
       this.view = view
    }

    override fun loadCategoryAPI(context: Context) {

        this.context = context

        view.showProgress(true)
        var observableCategory =  ApiService().loadCategory();

        getCategoryList(observableCategory)

    }

    override fun loadCategoryDb(context: Context) {
        var preferencesUtils = PreferencesUtils()

        view.updateView(preferencesUtils.getFromPreferences(context)!!)

        view.showProgress(false)

    }


    override fun addCategoryToDb(appDatabase: AppDatabase?, categoryResult: CategoryResult?) {
        for (category : Category in categoryResult!!.categories!!){
             onInsertCategory(category, appDatabase,appDatabase!!.categoryDao())
        }

        onInsertRanking(categoryResult.rankings,appDatabase, appDatabase!!.rankingDao())

    }

    fun onInsertCategory(category: Category, appDatabse: AppDatabase?, categoryDao: CategoryDao)
    {
        Observable.just(category)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->

                    /*Add child category with comma sperator*/
//                    var child_category =  TextUtils.join(",",item.childCategories) as String;
                    if(item.childCategories != null){
                        category.child_category = ActivityUtil().
                                convertStringArrayToString(item.childCategories!!,",");
                    }else{
                        category.child_category = ""
                    }
                    categoryDao.insertAll(category)
                    onInsertProduct(item.products as ArrayList<Product>, appDatabse,appDatabse!!.productDao())
                }, {

                    throwable -> throwable.printStackTrace()
                    Log.e(" TAG ", " Error--------");
                })


    }

    fun onInsertProduct(product: ArrayList<Product>, appDatabse: AppDatabase?,productDao: ProductDao)
    {
        Observable.just(product)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->

                    productDao.insertAll(product)

                    /*Add add variant for same*/
                    for (product_ in item){
                        var tax: Tax
                        tax = product_.tax!!
                        tax.p_id = product_.id

                        onInsertTax(tax,appDatabse!!.taxDao())

                        for (variant_ in product_.variants!!){
                            var variant: Variant
                            variant = variant_
                            variant.p_id = product_.id

                            onInsertVariant(
                                    variant,appDatabse!!.variantDao())
                        }

                    }

                }, {

                    throwable -> throwable.printStackTrace()
                    Log.e(" TAG ", "onInsertProduct Error--------");
                })


    }
    fun onInsertVariant(variant: Variant, variantDao: VariantDao)
    {
        Observable.just(variant)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    variantDao.insertAll(variant)

                }, {

                    throwable -> throwable.printStackTrace()
                    Log.e(" TAG ", "onInsertProduct Error--------");
                })


    }

    fun onInsertTax(variant: Tax, variantDao: TaxDao)
    {
        Observable.just(variant)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    variantDao.insertAll(variant)

                }, {

                    throwable -> throwable.printStackTrace()
                    Log.e(" TAG ", "onInsertProduct Error--------");
                })


    }

    fun onInsertRanking(ranking: List<Ranking>?,appDatabase: AppDatabase? ,rankingDao: RankingDao)
    {
        Observable.just(ranking)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    rankingDao.insertAll(ranking!!)
                    var count : Int = 1
                    for (product_ : Ranking  in item!!){


                        onInsertRankProduct(product_.ranking,product_.products, appDatabase!!.rankProductDao())
                        count++;
                    }


                }, {

                    throwable -> throwable.printStackTrace()
                    Log.e(" TAG ", "onInsertRanking Error--------");
                })


    }
    fun onInsertRankProduct(ranking_name : String,ranking_product: List<Product_>?, rankingDao: RankProductDao)
    {

        Observable.just(ranking_product)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    Log.e(" TAG ", "count--------"+ranking_name);
//                    if(count <= 1){
//                        rankingDao.insertAll(ranking_product!!)
//                    }else{
//                        rankingDao.updateAll(ranking_product!!)
//                    }

                    for (product_ : Product_ in item!!){
                        if(ranking_name.equals("Most Viewed Products")){
                            rankingDao.insertAll(ranking_product!!)
                        }else if(ranking_name.equals("Most OrdeRed Products")){
                            rankingDao.UpdateOrder_count(product_.id!!,
                                     product_.orderCount!!)
                        } else{
                            rankingDao.UpdateShare_count(product_.id!!,
                                     product_.shares!!)
                        }
                    }



                }, {

                    throwable -> throwable.printStackTrace()
                    Log.e(" TAG ", "onInsertRanking Error--------");
                })


    }

}