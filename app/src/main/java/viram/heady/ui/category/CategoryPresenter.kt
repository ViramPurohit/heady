package com.example.viram.heady_test.ui.category

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import viram.heady.MainApplication
import viram.heady.api.ApiService
import viram.heady.db.*
import viram.heady.model.*
import viram.heady.ui.category.CategoryImpl
import viram.heady.util.ActivityUtil


/**
 * Created by Viram Purohit on 12/1/2017.
 */
class CategoryPresenter  : CategoryImpl.Presenter{



    private val subscriptions = CompositeDisposable()

    lateinit var view : CategoryImpl.View

    lateinit var context : Context



    override fun loadCategoryCount(appDatabase: AppDatabase?) {
        Observable.just(appDatabase)
                .observeOn(Schedulers.io())
                .subscribe({ item ->
                    view.showCategoryCount(MainApplication.database!!.categoryDao().getCategoryCount())
                })


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

    fun getCategoryList(observableCategory: Observable<CategoryResult>) {

        var subscribe = observableCategory.subscribeOn(Schedulers.io())
                .subscribe({categoryResults: CategoryResult? ->
                    try {
                        var categoryResult = categoryResults?.categories
                        if(categoryResult!!.size  > 0){

                            view.updateView_DB(categoryResults)

//                            view.updateView_Rank(categoryResults!!.rankings!!)
                        }else{
                            view.showEmptyView(true)
                        }
                        view.showProgress(false)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        view.showProgress(false)
                    }
                })

        subscriptions.add(subscribe)

    }


    override fun loadCategoryDb(context: Context,appDatabase: AppDatabase?) {
        object : AsyncTask<Void, Void, List<Category>>() {
            override// this runs on another thread
            fun doInBackground(vararg params: Void): List<Category>? {
                var list: List<Category> ?= null
                try {
                    list = appDatabase!!.categoryDao().getAll()
                } catch (e: Exception) {
                    e.printStackTrace()
                    list = null
                }
                return list
            }

            override fun onPostExecute(result: List<Category>?) {
                super.onPostExecute(result)
                if(result != null){
                    if(result!!.size  > 0){
                        view.updateView_Local(result)
                    }else{
                        view.showEmptyView(true)
                    }
                    loadRankDb(appDatabase)
                }
                view.showProgress(false)
            }


        }.execute()

    }
    fun loadRankDb(appDatabase: AppDatabase?) {
        object : AsyncTask<Void, Void, List<Ranking>>() {
            override// this runs on another thread
            fun doInBackground(vararg params: Void): List<Ranking>? {
                var list: List<Ranking> ?= null
                try {
                    list = appDatabase!!.rankingDao().getAll()
                } catch (e: Exception) {
                    e.printStackTrace()
                    list = null
                }
                return list
            }

            override fun onPostExecute(result: List<Ranking>?) {
                super.onPostExecute(result)
                if(result != null){
                    if(result!!.size  > 0){
                        view.updateView_Rank(result)
                    }else{
                        view.showEmptyView(true)
                    }
                }

            }


        }.execute()

    }

    override fun addCategoryToDb(appDatabase: AppDatabase?, categoryResult: CategoryResult?) {
        object : AsyncTask<Void, Void, Void>() {
            override// this runs on another thread
            fun doInBackground(vararg params: Void): Void? {
                for (category : Category in categoryResult!!.categories!!){
                    onInsertCategory(category, appDatabase,appDatabase!!.categoryDao())
                }
                onInsertRanking(categoryResult.rankings,appDatabase, appDatabase!!.rankingDao())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                view.updateView_Local(categoryResult!!.categories!!)
            }


        }.execute()


    }

    fun onInsertCategory(category: Category, appDatabse: AppDatabase?, categoryDao: CategoryDao)
    {
        Observable.just(category)
                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
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
                    onInsertProduct(category.id,item.products as ArrayList<Product>,
                            appDatabse,appDatabse!!.productDao())
                }, {


                    throwable -> throwable.printStackTrace()
                    Log.e(" TAG ", " Error--------");
                })


    }

    fun onInsertProduct(id: Int?,product: ArrayList<Product>, appDatabse: AppDatabase?,productDao: ProductDao)
    {
        Observable.just(product)
                .observeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->



                    /*Add add variant for same*/
                    for (product_ in item){

                        var product : Product
                        /*Update Category id*/
                        product = product_
                        product.c_id = id

                        productDao.insertAll(product_)


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
//                .subscribeOn(AndroidSchedulers.mainThread())
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
//                .subscribeOn(AndroidSchedulers.mainThread())
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
//                .subscribeOn(AndroidSchedulers.mainThread())
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
//                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ item ->
                    Log.e(" TAG ", "count--------"+ranking_name);
                    rankingDao.insertAll(ranking_product!!)
//                    if(count <= 1){
//                        rankingDao.insertAll(ranking_product!!)
//                    }else{
//                        rankingDao.updateAll(ranking_product!!)
//                    }

                    for (product_ : Product_ in item!!){
                        if(ranking_name.equals("Most Viewed Products",true)){
                            rankingDao.UpdateView_count(product_.id!!,
                                    product_.viewCount!!)
                        }else if(ranking_name.equals("Most OrdeRed Products",true)){
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
    override fun getRankedProduct(appDatabase: AppDatabase?, ranking: String) {
        object : AsyncTask<Void, Void, ArrayList<Product>>() {
            var product: List<Product>?= null
            var sortingBy = 0
            override// this runs on another thread
            fun doInBackground(vararg params: Void): ArrayList<Product> {

                if(ranking.equals("Most Viewed Products",true)){
                    product = appDatabase!!.productDao().getProductByViewCount()
                    sortingBy = 1
                }else if(ranking.equals("Most OrdeRed Products",true)){
                    product =  appDatabase!!.productDao().getProductByOrderCount()
                    sortingBy = 2
                }else if(ranking.equals("Most ShaRed Products",true)){
                    product = appDatabase!!.productDao().getProductByShares()
                    sortingBy = 3
                }else{
                    sortingBy = 0

                }

                var categoryProduct = ArrayList<Product>()
                if(sortingBy > 0){
                    for (product_ in this!!.product!!){
                        product_.variants = appDatabase!!.variantDao().getVariant(product_.id!!)
                        product_.tax = appDatabase!!.taxDao().getTax(product_.id!!)

                        categoryProduct.add(product_)
                    }
                }

                return categoryProduct
            }

            override fun onPostExecute(result: ArrayList<Product>) {
                super.onPostExecute(result)
                view.updateView_Sorting(result!!,sortingBy)
            }


        }.execute()

    }

    override fun getSearchedProduct(appDatabase: AppDatabase?, product_name: String) {
        object : AsyncTask<Void, Void, List<Product>>() {
            var product: List<Product>?= null
            override// this runs on another thread
            fun doInBackground(vararg params: Void): List<Product> {

                product = appDatabase!!.productDao().getProductByName(product_name)

                return product!!
            }

            override fun onPostExecute(result: List<Product>) {
                super.onPostExecute(result)
                view.updateView_Sorting(result!!,4)
            }


        }.execute()

    }
}