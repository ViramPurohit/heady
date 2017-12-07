package com.example.viram.heady_test.ui.category

import android.arch.persistence.room.Room
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_category.view.*
import viram.heady.MainActivity
import viram.heady.MainApplication
import viram.heady.R
import viram.heady.db.AppDatabase
import viram.heady.inject.component.DaggerCategoryComponent
import viram.heady.inject.module.CategoryModule
import viram.heady.model.Category
import viram.heady.model.CategoryResult
import viram.heady.model.Product
import viram.heady.model.Ranking
import viram.heady.ui.category.CategoryImpl
import viram.heady.ui.category.CategoryListAdapter
import viram.heady.ui.category.SortingListAdapter
import viram.heady.ui.product.ProductFragment
import viram.heady.ui.product.ProductListAdapter
import viram.heady.ui.productdetails.ProductDetails
import viram.heady.ui.subcategory.SubCategoryFragment
import viram.heady.util.ActivityUtil
import viram.heady.util.CheckInternet
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

class CategoryFragment : Fragment(),CategoryImpl.View,SearchView.OnQueryTextListener{



    @Inject
    lateinit var categoryPresenter : CategoryImpl.Presenter

    var mview : View ?= null


    var sortingBy : Int = 1
    var filterBy : Int = 0

    var products: List<Product>?= null

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
        (activity as MainActivity).getSupportActionBar()!!.title = context.getString(R.string.category)
        setHasOptionsMenu(true);

        mview = inflater!!.inflate(R.layout.fragment_category, container, false)

        bindBottomSheet()

        mview!!.recyclerview.setLayoutManager(GridLayoutManager(context, 2))
        mview!!.recyclerview_ranking.setLayoutManager(GridLayoutManager(context, 1))


        categoryPresenter.loadCategoryCount(MainApplication.database)


        return mview
    }

    var mBottomSheetBehavior: BottomSheetBehavior<*>? = null
    fun bindBottomSheet(){
        val bottomsheet = mview!!.findViewById<View>(R.id.bottom_sheet)
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        (mBottomSheetBehavior as BottomSheetBehavior<View>?)!!.state = BottomSheetBehavior.STATE_COLLAPSED
        (mBottomSheetBehavior as BottomSheetBehavior<View>?)!!.setPeekHeight(0);


        (mBottomSheetBehavior as BottomSheetBehavior<View>?)!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    (mBottomSheetBehavior as BottomSheetBehavior<View>?)!!.peekHeight = 0
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        mview!!.btn_filter.setOnClickListener(View.OnClickListener { view ->
//            if(products != null){
//                if(products!!.size > 0){
                    if((mBottomSheetBehavior as BottomSheetBehavior<View>?)!!.state != BottomSheetBehavior.STATE_EXPANDED){
                        (mBottomSheetBehavior as BottomSheetBehavior<View>?)!!.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }else{
                        (mBottomSheetBehavior as BottomSheetBehavior<View>?)!!.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
//                }
//            }


        })
        mview!!.btn_sort.setOnClickListener(View.OnClickListener { view ->

            if(filterBy > 0){

                if(sortingBy == 1){
                    sortingBy = 2
                    mview!!.recyclerview.adapter = ProductListAdapter(context, products!!.reversed()!!,
                            object  : ProductListAdapter.OnItemClickListener{
                                override fun onItemClick(item: Product) {
                                    callFragment(item)

                                }
                            })
                }else{
                    sortingBy = 1
                    mview!!.recyclerview.adapter = ProductListAdapter(context,products!!,
                            object  : ProductListAdapter.OnItemClickListener{
                                override fun onItemClick(item: Product) {
                                    callFragment(item)

                                }
                            })
                }
            }

        })
    }
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu!!.clear()

        inflater!!.inflate(R.menu.menu_search,menu)
        var item = menu.findItem(R.id.action_search) as MenuItem

        val searchView = MenuItemCompat.getActionView(item) as SearchView

        searchView.setOnQueryTextListener(this)


        MenuItemCompat.setOnActionExpandListener(item,object : MenuItemCompat.OnActionExpandListener{
            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return true; // Return true to collapse action view
            }

            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {

                return true; // Return true to expand action view
            }


        })


    }

    override fun showCategoryCount(count: Int) {
        var checkInternet = CheckInternet()

        if(count > 0){
            categoryPresenter.loadCategoryDb(context,MainApplication.database)
        }else{
            if(checkInternet.isConnected(context)){
                categoryPresenter.loadCategoryAPI(context)
            }else{
                showEmptyView(true)
                showProgress(false)
                activity.runOnUiThread({
                    Toast.makeText(activity,
                            context.getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
                })


            }
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
    private fun injectDependency() {
        val categoryComponent = DaggerCategoryComponent.builder()
                .categoryModule(CategoryModule())
                .build()
        categoryComponent.inject(this)
    }


    override fun updateView_DB(categoryResult: CategoryResult?) {
        if (categoryResult != null) {


            categoryPresenter.addCategoryToDb(MainApplication.database,categoryResult)

            updateView_Rank(categoryResult!!.rankings!!)

        }

    }

    override fun updateView_Local(categoryResult: List<Category>) {
        if (categoryResult != null) {
            mview!!.recyclerview.adapter = CategoryListAdapter(categoryResult!!,
                    object  : CategoryListAdapter.OnItemClickListener{
                        override fun onItemClick(category: Category) {

                            if(category.child_category != null){

                                val ary = category.child_category!!.split(",".toRegex()).
                                        dropLastWhile { it.isEmpty() }.toTypedArray()

                                category.childCategories = convert(ary)


                                if(category.childCategories!!.size > 0){
                                    callChildCategory(category,categoryResult)
                                }else{
                                    callProductFragment(category)
                                }
                            }else{
                                callProductFragment(category)
                            }



                        }
                    })


        }
    }



    private fun convert(p_id: Array<String>): ArrayList<Int> { //Note the [] after the String.
        val number = ArrayList<Int>()
        for (_id in p_id){
            number.add(Integer.parseInt(_id))
        }

        return number
    }
    fun callProductFragment(item: Category/*, categoryResult: CategoryResult?*/) {
        val productFragment = ProductFragment()
        val bundle = Bundle()
        bundle.putInt("category_id", item.id!!)
        bundle.putString("category_name", item.name!!)
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
        Handler(Looper.getMainLooper()).post(Runnable {
            if(boolean){
                mview!!.progressbar.visibility = View.VISIBLE
            }else{
                mview!!.progressbar.visibility = View.GONE

            }

        })


    }

    override fun showError(error: String) {
        Handler(Looper.getMainLooper()).post(Runnable {
            mview!!.progressbar.visibility = View.GONE

        })

    }

    override fun showEmptyView(visible: Boolean) {
        mview!!.recyclerview.visibility = View.GONE
        mview!!.recyclerview_ranking.visibility = View.GONE
    }




    override fun updateView_Rank(ranking: List<Ranking>) {

        activity.runOnUiThread({
            var ranking_ = ArrayList<Ranking>()

            /*Add None at top*/
            var rank  = Ranking()
            rank.ranking = "None"
            rank.products = null

            ranking_.add(rank)

            ranking_.addAll(ranking)// as ArrayList<Ranking>;

            mview!!.recyclerview_ranking.adapter = SortingListAdapter(ranking_!!,
                    object  : SortingListAdapter.OnItemClickListener{
                        override fun onItemClick(ranking: Ranking) {

                            categoryPresenter.getRankedProduct(MainApplication.database,ranking.ranking)
                            (mBottomSheetBehavior as BottomSheetBehavior<View>?)!!.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        }
                    })
        })

    }


    override fun updateView_Sorting(product: List<Product>,orderby : Int) {
        filterBy = orderby
        if(orderby == 0){
            sortingBy = 1
            categoryPresenter.loadCategoryDb(context,MainApplication.database)
        }else{
            if (product != null) {
                if(orderby == 4){
                    mview!!.recyclerview.adapter = ProductListAdapter(context,product!!,
                            object  : ProductListAdapter.OnItemClickListener{
                                override fun onItemClick(item: Product) {
                                    callFragment(item)
                                }
                            })
                }else{
                    products = product
                    mview!!.recyclerview.adapter = ProductListAdapter(context,product!!,
                            object  : ProductListAdapter.OnItemClickListener{
                                override fun onItemClick(item: Product) {
                                    callFragment(item)
                                    sortingBy = 1
                                }
                            })
                }

            }
        }


    }

    fun callFragment(product: Product) {
        val productDetails = ProductDetails()
        val bundle = Bundle()
        bundle.putSerializable("product", product)
        productDetails.setArguments(bundle)
        ActivityUtil().addFragmentToActivity(
                fragmentManager,
                productDetails, R.id.frame, "productDetails")

    }
    override fun onQueryTextChange(search_string: String?): Boolean {

        if(search_string!!.length == 0){
            sortingBy = 1
            categoryPresenter.loadCategoryDb(context,MainApplication.database)
        }
        return true;
    }

    override fun onQueryTextSubmit(search_string: String?): Boolean {
        if (search_string != null) {
            categoryPresenter.getSearchedProduct(MainApplication.database,search_string)
        }
        return false;
    }
}
