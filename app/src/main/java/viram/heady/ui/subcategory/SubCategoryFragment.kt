package viram.heady.ui.subcategory


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_child.view.*
import viram.heady.MainActivity

import viram.heady.R
import viram.heady.inject.component.DaggerSubCategoryComponent
import viram.heady.inject.module.SubCategoryModule
import viram.heady.model.Category
import viram.heady.ui.category.CategoryListAdapter
import viram.heady.ui.product.ProductFragment
import viram.heady.util.ActivityUtil
import javax.inject.Inject
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class SubCategoryFragment : Fragment(), SubCategoryImpl.View {



    @Inject
    lateinit var subCategoryPresenter : SubCategoryImpl.Presenter

    lateinit var mview : View

    lateinit var category : Category
    lateinit var categoryResult : ArrayList<Category>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        val bundle = this.arguments
        if (bundle != null) {
            category = bundle.getSerializable("category") as Category
            categoryResult = bundle.getSerializable("categoryResult") as ArrayList<Category>

        }
        injectDependency()
        subCategoryPresenter.attachView(this)

    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mview = inflater!!.inflate(R.layout.fragment_child, container, false)
        /*Update title details*/
        (activity as MainActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).getSupportActionBar()!!.setTitle(category.name)
         setHasOptionsMenu(true);

        subCategoryPresenter.getSubCategory(category,categoryResult)

        return mview
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {

                activity.onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun injectDependency() {
        val subcategoryComponent = DaggerSubCategoryComponent.builder()
                .subCategoryModule(SubCategoryModule())
                .build()
        subcategoryComponent.inject(this)
    }

    override fun updateView(categorieslist: ArrayList<Category>) {
        mview!!.recyclerview_child_category.setLayoutManager(GridLayoutManager(context, 2))

        if (categorieslist != null) {
            mview!!.recyclerview_child_category.adapter = CategoryListAdapter(categorieslist!!,
                    object  : CategoryListAdapter.OnItemClickListener{
                        override fun onItemClick(category: Category) {
                            if(category.childCategories!!.size > 0){
                                callChildCategory(category,categoryResult)
                            }else{
                                callProductFragment(category)
                            }

                        }
                    })
        }

    }
    fun callProductFragment(category: Category) {
        val productFragment = ProductFragment()
        val bundle = Bundle()
        bundle.putInt("category", category.id!!)
        bundle.putString("category_name", category.name!!)
//        bundle.putSerializable("categoryResult", categoryResult)
        productFragment.setArguments(bundle)
        ActivityUtil().addFragmentToActivity(
                fragmentManager,
                productFragment, R.id.frame, "productFragment")
    }
    fun callChildCategory(category: Category, categoryResult: ArrayList<Category>) {

        val childCategory = SubCategoryFragment()

        val bundle = Bundle()
        bundle.putSerializable("category", category)
        bundle.putSerializable("categoryResult", categoryResult)
        childCategory.setArguments(bundle)
        ActivityUtil().addFragmentToActivity(
                fragmentManager,
                childCategory, R.id.frame, "subCategoryFragment")

    }

}
