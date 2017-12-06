package viram.heady.ui.product


import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.GridLayoutManager
import android.view.*
import android.support.v7.widget.SearchView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_product.view.*
import viram.heady.MainActivity
import viram.heady.MainApplication
import viram.heady.R
import viram.heady.inject.component.DaggerProductComponent
import viram.heady.inject.module.ProductModule
import viram.heady.model.Product
import viram.heady.ui.productdetails.ProductDetails
import viram.heady.util.ActivityUtil
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment(),ProductImpl.View ,SearchView.OnQueryTextListener{



    @Inject
    lateinit var productPresenter : ProductImpl.Presenter

    lateinit var mView : View

//    lateinit var category : Category

//    lateinit var categoryResult: CategoryResult

    var categor_id : Int = 0

    lateinit var category_name : String

    fun instance() : ProductFragment{
        return ProductFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDepedency()

        productPresenter.attachView(this)

        val bundle = this.arguments
        if (bundle != null) {
            categor_id = bundle.getInt("category_id",0)
            category_name = bundle.getString("category_name")
//            categoryResult = bundle.getSerializable("categoryResult") as CategoryResult
        }

    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater!!.inflate(R.layout.fragment_product, container, false)

        /*Update title details*/
        (activity as MainActivity).getSupportActionBar()!!.setTitle(category_name)
        (activity as MainActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true);

//        val coordinatorLayout = mView.findViewById<View>(R.id.main_content) as CoordinatorLayout
        val bottomsheet = mView.findViewById<View>(R.id.bottom_sheet)
        var mBottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        mBottomSheetBehavior.setPeekHeight(0);


        mBottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.peekHeight = 0
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        mView.btn_sort.setOnClickListener(View.OnClickListener { view ->
            if(mBottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED){
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }else{
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }




        })

        mView!!.product_recyclerview.setLayoutManager(GridLayoutManager(context, 2))

        productPresenter.loadProduct(MainApplication.database,categor_id)

        return mView
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
    override fun updateView(categoryProduct: List<Product>?) {



        if (categoryProduct != null) {
            mView!!.product_recyclerview.adapter = ProductListAdapter(context,categoryProduct!!,
                    object  : ProductListAdapter.OnItemClickListener{
                        override fun onItemClick(item: Product) {
                            callFragment(item)
                            Toast.makeText(activity," item "+item.name, Toast.LENGTH_SHORT).show()
                        }
                    })
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        productPresenter.unSubscribe()
    }

    fun injectDepedency(){
        var productComponent = DaggerProductComponent.builder()
                .productModule(ProductModule())
                .build()
        productComponent.inject(this)
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

    override fun onQueryTextChange(p0: String?): Boolean {

        return true;
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return false;
    }
}
