package com.example.viram.heady_test.ui.category

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_category.view.*
import viram.heady.MainActivity
import viram.heady.R
import viram.heady.inject.component.DaggerCategoryComponent
import viram.heady.inject.module.CategoryModule
import viram.heady.model.Category
import viram.heady.model.CategoryResult
import viram.heady.ui.category.CategoryImpl
import viram.heady.ui.category.CategoryListAdapter
import viram.heady.ui.subcategory.SubCategoryFragment
import viram.heady.ui.product.ProductFragment
import viram.heady.util.ActivityUtil
import viram.heady.util.CheckInternet
import viram.heady.util.PreferencesUtils
import java.util.ArrayList
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */

class CategoryFragment : Fragment(),CategoryImpl.View{

    @Inject
    lateinit var categoryPresenter : CategoryImpl.Presenter

    var mview : View ?= null



    fun newInstance(): CategoryFragment {
        return CategoryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDependency()
        categoryPresenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        /*Update title details*/
        (activity as MainActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).getSupportActionBar()!!.setTitle(
                context.getString(R.string.category))

        mview = inflater!!.inflate(R.layout.fragment_category, container, false)


        var checkInternet = CheckInternet()

        if(PreferencesUtils().isRecordWithCache(context)!!){
            categoryPresenter.loadCategoryDb(context)
        }else{
            if(checkInternet.isConnected(context)){
                categoryPresenter.loadCategoryAPI(context)
            }
        }
        return mview
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        categoryPresenter.subscribe()
    }


    override fun onDestroyView() {
        super.onDestroyView()
//        categoryPresenter.unSubscribe()
    }
    private fun injectDependency() {
        val categoryComponent = DaggerCategoryComponent.builder()
                .categoryModule(CategoryModule())
                .build()
        categoryComponent.inject(this)
    }


    override fun updateView(categoryResult: CategoryResult?) {


        mview!!.recyclerview.setLayoutManager(GridLayoutManager(context, 2))

        if (categoryResult != null) {
            mview!!.recyclerview.adapter = CategoryListAdapter(categoryResult.categories!!,
                    object  : CategoryListAdapter.OnItemClickListener{
                        override fun onItemClick(category: Category) {

                            if(category.childCategories!!.size > 0){
                                callChildCategory(category,categoryResult.categories)
                            }else{
                                callProductFragment(category)
                            }

                        }
                    })
        }

    }

    fun callProductFragment(item: Category) {
        val productFragment = ProductFragment()
        val bundle = Bundle()
        bundle.putSerializable("category", item)
        productFragment.setArguments(bundle)
        ActivityUtil().addFragmentToActivity(
                fragmentManager,
                productFragment, R.id.frame, "productFragment")
    }
    fun callChildCategory(item: Category, categoryResult: List<Category>?) {
        val childCategory = SubCategoryFragment()

        val bundle = Bundle()
        bundle.putSerializable("category", item)
        bundle.putSerializable("categoryResult", categoryResult as ArrayList<Category>)
        childCategory.setArguments(bundle)
        ActivityUtil().addFragmentToActivity(
                fragmentManager,
                childCategory, R.id.frame, "subCategoryFragment")
    }
    override fun showProgress(boolean: Boolean) {
        if(boolean){
            mview!!.progressbar.visibility = View.VISIBLE
        }else{
            mview!!.progressbar.visibility = View.GONE

        }

    }

    override fun showError(error: String) {
        mview!!.progressbar.visibility = View.GONE
    }

    override fun showEmptyView(visible: Boolean) {
    }


}
