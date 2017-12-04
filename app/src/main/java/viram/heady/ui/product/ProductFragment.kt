package viram.heady.ui.product


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_product.view.*
import viram.heady.MainActivity
import viram.heady.R
import viram.heady.inject.component.DaggerProductComponent
import viram.heady.inject.module.ProductModule
import viram.heady.model.Category
import viram.heady.model.Product
import viram.heady.ui.productdetails.ProductDetails
import viram.heady.util.ActivityUtil
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment(),ProductImpl.View {


    @Inject
    lateinit var productPresenter : ProductImpl.Presenter

    lateinit var mView : View

    lateinit var category : Category
    fun instance() : ProductFragment{
        return ProductFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDepedency()

        productPresenter.attachView(this)

        val bundle = this.arguments
        if (bundle != null) {
            category = bundle.getSerializable("category") as Category
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater!!.inflate(R.layout.fragment_product, container, false)

        /*Update title details*/
        (activity as MainActivity).getSupportActionBar()!!.setTitle(category.name)
        (activity as MainActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true);


        productPresenter.loadProduct()

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

    override fun updateView() {

        mView!!.product_recyclerview.setLayoutManager(GridLayoutManager(context, 2))

        if (category != null) {
            mView!!.product_recyclerview.adapter = ProductListAdapter(context,category.products!!,
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
}// Required empty public constructor
