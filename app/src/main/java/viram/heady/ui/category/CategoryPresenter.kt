package com.example.viram.heady_test.ui.category

import android.content.Context
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import viram.heady.api.ApiService
import viram.heady.model.CategoryResult
import viram.heady.ui.category.CategoryImpl
import viram.heady.util.PreferencesUtils


/**
 * Created by Viram Purohit on 12/1/2017.
 */
class CategoryPresenter : CategoryImpl.Presenter{

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

    override fun loadCategoryDb() : CategoryResult {
        var preferencesUtils = PreferencesUtils()

        return preferencesUtils.getFromPreferences(context)
    }
}